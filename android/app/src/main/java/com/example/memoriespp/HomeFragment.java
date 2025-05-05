package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import network.GroupApi;
import network.GroupResponse;
import network.ScheduleApi;
import network.ScheduleResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private LinearLayout scheduleContainer;
    private ScheduleApi  scheduleApi;
    private GroupApi     groupApi;
    private int userId, groupId;
    private String role;
    private FrameLayout teachersFrameLayout;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // 1) Ustawienie napisu "Dziś jest <dzień tygodnia>, <dd.MM.yyyy>"
        TextView dateNow = root.findViewById(R.id.dateNow);
        Calendar cal = Calendar.getInstance();
        String[] dni = {
                "niedziela","poniedziałek","wtorek",
                "środa","czwartek","piątek","sobota"
        };
        int idx = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String data = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                .format(cal.getTime());
        dateNow.setText("Dziś jest " + dni[idx] + ", " + data);

        // 2) Przygotowanie reszty widoków
        scheduleContainer   = root.findViewById(R.id.scheduleContainer);
        teachersFrameLayout = root.findViewById(R.id.teacherLayout);

        if (getArguments() != null) {
            role   = getArguments().getString("role");
            userId = getArguments().getInt("userId", -1);
        }
        teachersFrameLayout.setVisibility("T".equals(role)
                ? View.GONE
                : View.VISIBLE
        );

        Retrofit rt = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        scheduleApi = rt.create(ScheduleApi.class);
        groupApi    = rt.create(GroupApi   .class);

        if ("S".equals(role))      loadStudentSchedule();
        else if ("T".equals(role)) loadTeacherSchedule();

        return root;
    }

    private void loadStudentSchedule() {
        groupApi.getGroupsForUser(userId)
                .enqueue(new Callback<List<GroupResponse>>() {
                    @Override public void onResponse(Call<List<GroupResponse>> c,
                                                     Response<List<GroupResponse>> r) {
                        if (!r.isSuccessful() || r.body()==null || r.body().isEmpty()) return;
                        groupId = r.body().get(0).getId();
                        fetchAndShow((api,f,t)->api.getScheduleForGroup(groupId,f,t));
                    }
                    @Override public void onFailure(Call<List<GroupResponse>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd ładowania grupy", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadTeacherSchedule() {
        fetchAndShow((api,f,t)->api.getScheduleForTeacher(userId,f,t));
    }

    private void fetchAndShow(ScheduleLoader loader) {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        scheduleContainer.removeAllViews();
        loader.load(scheduleApi, today, today)
                .enqueue(new Callback<List<ScheduleResponseDTO>>() {
                    @Override public void onResponse(Call<List<ScheduleResponseDTO>> c,
                                                     Response<List<ScheduleResponseDTO>> r) {
                        if (!r.isSuccessful() || r.body()==null) return;
                        for (ScheduleResponseDTO dto : r.body()) {
                            View item = LayoutInflater.from(getContext())
                                    .inflate(R.layout.item_home_lesson,
                                            scheduleContainer, false);

                            TextView tv1 = item.findViewById(R.id.tvSubject);
                            TextView tv2 = item.findViewById(R.id.tvTeacher);
                            TextView tv3 = item.findViewById(R.id.tvTime);

                            if ("S".equals(role)) {
                                tv1.setText(dto.getSubjectName());
                                tv2.setText(dto.getTeacherName());
                            } else {
                                tv1.setText(dto.getSubjectName());
                                tv2.setText(dto.getGroupName());
                            }

                            // scal date + time w tvTime
                            LocalDate ld = LocalDate.parse(dto.getLessonDate());
                            String pretty = ld.format(
                                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            );
                            String times = dto.getStartTime().substring(0,5)
                                    + " – " + dto.getEndTime().substring(0,5);
                            tv3.setText(pretty + "   " + times);

                            scheduleContainer.addView(item);
                        }
                    }
                    @Override public void onFailure(Call<List<ScheduleResponseDTO>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private interface ScheduleLoader {
        Call<List<ScheduleResponseDTO>> load(
                ScheduleApi api, String from, String to
        );
    }
}
