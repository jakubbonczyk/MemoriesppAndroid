package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import network.AssignmentResponse;
import network.ClassApi;
import network.ClassResponse;
import network.GroupApi;
import network.GroupResponse;
import network.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AssignTeacherToClassFragment extends Fragment {

    private Spinner chooseGroupSpinner;
    private LinearLayout teachersContainer;
    private AppCompatButton saveBtn;

    private GroupApi groupApi;
    private ClassApi classApi;

    private int selectedGroupId;

    private List<GroupResponse> groupList = new ArrayList<>();
    private List<ClassResponse> classList = new ArrayList<>();

    private List<UserResponse> teachers = new ArrayList<>();
    private List<Spinner> spinners = new ArrayList<>();
    private List<Boolean> isAssigned = new ArrayList<>();
    private Set<Integer> alreadyAssignedSubjects = new HashSet<>();


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_assign_teacher_to_class,
                container,
                false);

        chooseGroupSpinner = root.findViewById(R.id.chooseGroupSpinner);
        teachersContainer  = root.findViewById(R.id.teacherclassLayout);
        saveBtn            = root.findViewById(R.id.addNewClassButton);
        saveBtn.setText("Zapisz");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        groupApi = retrofit.create(GroupApi.class);
        classApi = retrofit.create(ClassApi.class);

        loadClasses();
        loadGroups();

        chooseGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {
                selectedGroupId = groupList.get(position).getId();
                alreadyAssignedSubjects.clear();

                // najpierw pobierz przypisania, potem załaduj nauczycieli
                loadAlreadyAssignedSubjects(() -> loadTeachersForGroup(selectedGroupId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        saveBtn.setOnClickListener(v -> saveAssignments());

        return root;
    }

    private void loadClasses() {
        classApi.getAllClasses()
                .enqueue(new Callback<List<ClassResponse>>() {
                    @Override public void onResponse(Call<List<ClassResponse>> c,
                                                     Response<List<ClassResponse>> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            classList = r.body();
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania przedmiotów: " + r.code(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override public void onFailure(Call<List<ClassResponse>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci przy przedmiotach: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadGroups() {
        groupApi.getAllGroups()
                .enqueue(new Callback<List<GroupResponse>>() {
                    @Override public void onResponse(Call<List<GroupResponse>> c,
                                                     Response<List<GroupResponse>> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            groupList = r.body();
                            List<String> names = new ArrayList<>();
                            for (GroupResponse g : groupList) {
                                names.add(g.getGroupName());
                            }
                            ArrayAdapter<String> ad = new ArrayAdapter<>(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    names
                            );
                            ad.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item
                            );
                            chooseGroupSpinner.setAdapter(ad);
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania grup: " + r.code(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override public void onFailure(Call<List<GroupResponse>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci przy grupach: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadTeachersForGroup(int groupId) {
        groupApi.getTeachersByGroup(groupId)
                .enqueue(new Callback<List<UserResponse>>() {
                    @Override public void onResponse(Call<List<UserResponse>> c,
                                                     Response<List<UserResponse>> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            populateTeacherRows(groupId, r.body());
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania nauczycieli: " + r.code(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override public void onFailure(Call<List<UserResponse>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci przy nauczycielach: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadAlreadyAssignedSubjects(Runnable onComplete) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GroupApi api = retrofit.create(GroupApi.class);
        api.getAssignmentsForGroup(selectedGroupId).enqueue(new Callback<List<AssignmentResponse>>() {
            @Override
            public void onResponse(Call<List<AssignmentResponse>> call, Response<List<AssignmentResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (AssignmentResponse assignment : response.body()) {
                        alreadyAssignedSubjects.add(assignment.getClassId());
                    }
                }
                onComplete.run(); // kontynuuj
            }

            @Override
            public void onFailure(Call<List<AssignmentResponse>> call, Throwable t) {
                Toast.makeText(requireContext(), "Błąd podczas pobierania przypisań", Toast.LENGTH_SHORT).show();
                onComplete.run(); // mimo błędu kontynuuj
            }
        });
    }



    private void populateTeacherRows(int groupId, List<UserResponse> list) {
        teachersContainer.removeAllViews();
        spinners.clear();
        isAssigned.clear();
        teachers = list;

        // przygotuj nazwy przedmiotów
        List<String> classNames = new ArrayList<>();
        for (ClassResponse cls : classList) {
            classNames.add(cls.getClassName());
        }

        for (int i = 0; i < list.size(); i++) {
            final int idx = i;
            final int localUserId = list.get(i).getId();
            final int finalGroupId = groupId;

            View row = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_teacher_class, teachersContainer, false);

            TextView tv = row.findViewById(R.id.teacherNameTv);
            Spinner sp = row.findViewById(R.id.subjectSpinner);
            tv.setText(list.get(i).getName() + " " + list.get(i).getSurname());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    classNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);
            sp.setEnabled(true);

            spinners.add(sp);
            isAssigned.add(false);

            // pobierz i ustaw już przypisane
            classApi.getAssignedClasses(localUserId, finalGroupId)
                    .enqueue(new Callback<List<ClassResponse>>() {
                        @Override
                        public void onResponse(Call<List<ClassResponse>> call,
                                               Response<List<ClassResponse>> resp) {
                            if (resp.isSuccessful() && resp.body()!=null
                                    && !resp.body().isEmpty()) {
                                ClassResponse assigned = resp.body().get(0);
                                for (int j = 0; j < classList.size(); j++) {
                                    if (classList.get(j).getId()
                                            .equals(assigned.getId())) {
                                        sp.setSelection(j);
                                        sp.setEnabled(false);
                                        isAssigned.set(idx, true);
                                        break;
                                    }
                                }
                            }
                        }
                        @Override public void onFailure(Call<List<ClassResponse>> c, Throwable t) { }
                    });

            teachersContainer.addView(row);
        }
    }

    private void saveAssignments() {
        // Mapa: classId -> userId (lokalne przypisania w tym formularzu)
        Map<Integer, Integer> assignedSubjects = new HashMap<>();

        for (int i = 0; i < teachers.size(); i++) {
            if (isAssigned.get(i)) continue;

            int classPos = spinners.get(i).getSelectedItemPosition();
            int classId = classList.get(classPos).getId();
            int userId = teachers.get(i).getId();

            // WALIDACJA: sprawdź, czy przedmiot nie był już przypisany wcześniej
            if (alreadyAssignedSubjects.contains(classId)) {
                Toast.makeText(requireContext(),
                        "Przedmiot \"" + classList.get(classPos).getClassName() + "\" został już wcześniej przypisany innemu nauczycielowi.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // WALIDACJA: sprawdź, czy nie jest też przypisany w bieżącym formularzu
            if (assignedSubjects.containsKey(classId)) {
                Toast.makeText(requireContext(),
                        "Przedmiot \"" + classList.get(classPos).getClassName() + "\" został przypisany więcej niż raz.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            assignedSubjects.put(classId, userId);

            // Wyślij przypisanie do backendu
            classApi.assignTeacherToClass(userId, selectedGroupId, classId)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> c, Response<String> r) {
                            // Można dodać reakcję po sukcesie, jeśli potrzeba
                        }

                        @Override
                        public void onFailure(Call<String> c, Throwable t) {
                            Toast.makeText(requireContext(),
                                    "Błąd podczas przypisywania przedmiotu: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        Toast.makeText(requireContext(),
                "Zapisano nowe przypisania", Toast.LENGTH_SHORT).show();

        // Odśwież fragment
        AssignTeacherToClassFragment assignTeacherToClassFragment = new AssignTeacherToClassFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, assignTeacherToClassFragment)
                .addToBackStack(null)
                .commit();
    }


}
