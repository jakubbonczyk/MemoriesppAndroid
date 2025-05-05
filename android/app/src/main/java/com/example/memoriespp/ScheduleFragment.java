package com.example.memoriespp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import network.AssignmentDTO;
import network.GroupApi;
import network.GroupMemberClassApi;
import network.GroupResponse;
import network.ScheduleApi;
import network.ScheduleRequestDTO;
import network.ScheduleResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleFragment extends Fragment {

    private Spinner groupSpinner, assignmentSpinner, startTimeSpinner, endTimeSpinner;
    private Button dateBtn, addLessonBtn;
    private LinearLayout lessonListContainer;

    private GroupApi groupApi;
    private GroupMemberClassApi assignmentApi;
    private ScheduleApi scheduleApi;

    private LocalDate selectedDate;
    private List<GroupResponse> groupList = new ArrayList<>();
    private List<AssignmentDTO> assignmentList = new ArrayList<>();

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inf.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        groupSpinner        = v.findViewById(R.id.groupSpinner);
        assignmentSpinner   = v.findViewById(R.id.assignmentSpinner);
        dateBtn             = v.findViewById(R.id.dateBtn);
        startTimeSpinner    = v.findViewById(R.id.startTimeSpinner);
        endTimeSpinner      = v.findViewById(R.id.endTimeSpinner);
        addLessonBtn        = v.findViewById(R.id.addLessonBtn);
        lessonListContainer = v.findViewById(R.id.lessonListContainer);

        Retrofit rt = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        groupApi      = rt.create(GroupApi.class);
        assignmentApi = rt.create(GroupMemberClassApi.class);
        scheduleApi   = rt.create(ScheduleApi.class);

        initTimeSpinners();
        initDatePicker();
        loadGroups();

        addLessonBtn.setOnClickListener(x -> onAddLesson());
    }

    private void loadGroups() {
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override public void onResponse(Call<List<GroupResponse>> c,
                                             Response<List<GroupResponse>> r) {
                if (!r.isSuccessful() || r.body()==null) return;
                groupList = r.body();
                List<String> names = new ArrayList<>();
                for (GroupResponse g: groupList) names.add(g.getGroupName());
                ArrayAdapter<String> ad = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        names
                );
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupSpinner.setAdapter(ad);
                groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id){
                        int gid = groupList.get(pos).getId();
                        loadAssignments(gid);
                    }
                    @Override public void onNothingSelected(AdapterView<?> p){}
                });
            }
            @Override public void onFailure(Call<List<GroupResponse>> c, Throwable t){
                Toast.makeText(getContext(),"Błąd ładowania grup",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAssignments(int groupId) {
        assignmentApi.getAssignments(groupId)
                .enqueue(new Callback<List<AssignmentDTO>>() {
                    @Override public void onResponse(Call<List<AssignmentDTO>> c,
                                                     Response<List<AssignmentDTO>> r) {
                        if (!r.isSuccessful() || r.body()==null) return;
                        assignmentList = r.body();
                        List<String> labels = new ArrayList<>();
                        for (AssignmentDTO a: assignmentList)
                            labels.add(a.getTeacherName()+" — "+a.getClassName());
                        ArrayAdapter<String> ad = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                labels
                        );
                        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        assignmentSpinner.setAdapter(ad);
                    }
                    @Override public void onFailure(Call<List<AssignmentDTO>> c, Throwable t){
                        Toast.makeText(getContext(),"Błąd ładowania przypisań",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTimeSpinners() {
        ArrayAdapter<CharSequence> ta = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.times,
                android.R.layout.simple_spinner_item
        );
        ta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTimeSpinner.setAdapter(ta);
        endTimeSpinner  .setAdapter(ta);
    }

    private void initDatePicker() {
        dateBtn.setOnClickListener(x -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(requireContext(),
                    (dp,y,m,d) -> {
                        selectedDate = LocalDate.of(y, m+1, d);
                        dateBtn.setText(selectedDate.format(DateTimeFormatter.ISO_DATE));
                    },
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void onAddLesson() {
        if (selectedDate==null || assignmentList.isEmpty()) {
            Toast.makeText(getContext(),
                    "Wybierz grupę, datę i nauczyciela", Toast.LENGTH_SHORT).show();
            return;
        }
        int pos = assignmentSpinner.getSelectedItemPosition();
        AssignmentDTO a = assignmentList.get(pos);
        String start = startTimeSpinner.getSelectedItem().toString() + ":00";
        String end   = endTimeSpinner  .getSelectedItem().toString() + ":00";

        ScheduleRequestDTO dto = new ScheduleRequestDTO(
                a.getAssignmentId(),
                selectedDate.format(DateTimeFormatter.ISO_DATE),
                start, end
        );

        scheduleApi.createLesson(dto).enqueue(new Callback<ScheduleResponseDTO>() {
            @Override public void onResponse(Call<ScheduleResponseDTO> c,
                                             Response<ScheduleResponseDTO> r) {
                if (!r.isSuccessful() || r.body()==null) {
                    Toast.makeText(getContext(),"Nie udało się dodać",Toast.LENGTH_SHORT).show();
                    return;
                }
                // dopisz do listy
                ScheduleResponseDTO res = r.body();
                TextView tv = new TextView(requireContext());
                tv.setText(
                        res.getLessonDate() + " | " +
                                res.getTeacherName() + " — " + res.getSubjectName() +
                                " | " + res.getStartTime().substring(0,5) +
                                "–" + res.getEndTime().substring(0,5)
                );
                tv.setPadding(0,8,0,8);
                lessonListContainer.addView(tv);
            }
            @Override public void onFailure(Call<ScheduleResponseDTO> c, Throwable t){
                Toast.makeText(getContext(),"Błąd sieci",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
