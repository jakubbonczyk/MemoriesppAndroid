package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.GradeApi;
import network.User;
import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddGradesFragment extends Fragment {

    private Spinner studentSpinner;
    private Spinner gradeSpinner;
    private EditText descriptionEditText;

    private List<User> studentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_grades, container, false);

        studentSpinner = rootView.findViewById(R.id.studentsSpinner);
        gradeSpinner = rootView.findViewById(R.id.gradesSpinner);
        descriptionEditText = rootView.findViewById(R.id.editTextTextMultiLine);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"1", "2", "3", "4", "5", "6"});
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        fetchStudentsFromGroup(1);

        AppCompatButton addGradeButton = rootView.findViewById(R.id.addGradeButton);
        addGradeButton.setOnClickListener(view -> {
            int selectedStudentIndex = studentSpinner.getSelectedItemPosition();
            if (selectedStudentIndex < 0 || selectedStudentIndex >= studentList.size()) {
                Toast.makeText(getContext(), "Nie wybrano ucznia", Toast.LENGTH_SHORT).show();
                return;
            }

            User selectedStudent = studentList.get(selectedStudentIndex);
            String gradeStr = (String) gradeSpinner.getSelectedItem();
            String description = descriptionEditText.getText().toString();

            int gradeValue;
            try {
                gradeValue = Integer.parseInt(gradeStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Niepoprawna ocena", Toast.LENGTH_SHORT).show();
                return;
            }

            int teacherId = getActivity().getIntent().getIntExtra("userId", -1);
            if (teacherId == -1) {
                Toast.makeText(getContext(), "Nie można odczytać ID nauczyciela", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> gradeData = new HashMap<>();
            gradeData.put("grade", gradeValue);
            gradeData.put("description", description);
            gradeData.put("studentId", selectedStudent.getId());
            gradeData.put("teacherId", teacherId);
            gradeData.put("classId", 1);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GradeApi gradeApi = retrofit.create(GradeApi.class);

            gradeApi.addGrade(gradeData).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Pomyślnie dodano ocenę", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new GradesFragment())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(getContext(), "Błąd: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "Błąd połączenia: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return rootView;
    }

    private void fetchStudentsFromGroup(int groupId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getStudentsByGroup(groupId)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            studentList = response.body();
                            List<String> studentNames = new ArrayList<>();
                            for (User u : studentList) {
                                studentNames.add(u.getName() + " " + u.getSurname());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    studentNames
                            );
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            studentSpinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "Nie udało się pobrać uczniów: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
