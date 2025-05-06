package com.example.memoriespp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.GradeApi;
import network.GroupApi;
import network.User;
import network.UserApi;
import network.ClassDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddGradesFragment extends Fragment {

    private Spinner studentSpinner;
    private Spinner gradeSpinner;
    private EditText typeEditText;
    private EditText descriptionEditText;
    private TextView subjectNameTv;

    private List<User> studentList = new ArrayList<>();

    private int groupId;
    private int teacherId;
    private int classId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            groupId = getArguments().getInt("groupId", -1);
        }
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        teacherId = prefs.getInt("userId", -1);

        if (groupId < 0 || teacherId < 0) {
            Toast.makeText(getContext(),
                    "Brak ID grupy lub nauczyciela", Toast.LENGTH_SHORT).show();
            return;
        }

        studentSpinner      = view.findViewById(R.id.studentsSpinner);
        subjectNameTv       = view.findViewById(R.id.subjectNameTv);
        gradeSpinner        = view.findViewById(R.id.gradesSpinner);
        typeEditText        = view.findViewById(R.id.typeEditText);
        descriptionEditText = view.findViewById(R.id.editTextTextMultiLine);
        AppCompatButton addBtn = view.findViewById(R.id.addGradeButton);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"1","2","3","4","5","6"});
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        fetchStudentsFromGroup(groupId);
        fetchSubjectForGroupAndTeacher(groupId, teacherId);

        addBtn.setOnClickListener(v -> {
            int stuPos = studentSpinner.getSelectedItemPosition();
            if (stuPos < 0 || stuPos >= studentList.size()) {
                Toast.makeText(getContext(), "Wybierz ucznia", Toast.LENGTH_SHORT).show();
                return;
            }
            User selectedStudent = studentList.get(stuPos);

            String gradeStr = (String) gradeSpinner.getSelectedItem();
            int gradeValue;
            try {
                gradeValue = Integer.parseInt(gradeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Niepoprawna ocena", Toast.LENGTH_SHORT).show();
                return;
            }

            String typeStr = typeEditText.getText().toString().trim();
            if (typeStr.isEmpty()) {
                Toast.makeText(getContext(), "Podaj typ oceny", Toast.LENGTH_SHORT).show();
                return;
            }

            String desc = descriptionEditText.getText().toString().trim();

            Map<String,Object> body = new HashMap<>();
            body.put("grade",       gradeValue);
            body.put("description", desc);
            body.put("type",        typeStr);
            body.put("studentId",   selectedStudent.getId());
            body.put("teacherId",   teacherId);
            body.put("classId",     classId);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GradeApi gradeApi = retrofit.create(GradeApi.class);
            gradeApi.addGrade(body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> res) {
                    if (res.isSuccessful()) {
                        Toast.makeText(getContext(),
                                "Pomyślnie dodano ocenę", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new GradesFragment())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(getContext(),
                                "Błąd serwera: " + res.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "Błąd sieci: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void fetchStudentsFromGroup(int groupId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getStudentsByGroup(groupId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    studentList = res.body();
                    List<String> names = new ArrayList<>();
                    for (User u : studentList) {
                        names.add(u.getName() + " " + u.getSurname());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            names
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    studentSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(),
                            "Nie udało się pobrać uczniów: " + res.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSubjectForGroupAndTeacher(int groupId, int teacherId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GroupApi groupApi = retrofit.create(GroupApi.class);
        groupApi.getSubjectForGroupAndTeacher(groupId, teacherId)
                .enqueue(new Callback<ClassDTO>() {
                    @Override
                    public void onResponse(Call<ClassDTO> call, Response<ClassDTO> res) {
                        if (res.isSuccessful() && res.body() != null) {
                            classId = res.body().getId();
                            subjectNameTv.setText(res.body().getClassName());
                        } else {
                            Toast.makeText(getContext(),
                                    "Brak przedmiotu dla tej grupy/nauczyciela",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClassDTO> call, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
