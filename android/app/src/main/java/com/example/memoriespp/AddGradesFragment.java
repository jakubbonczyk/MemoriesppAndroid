package com.example.memoriespp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.ClassDTO;
import network.GradeApi;
import network.GroupApi;
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
    private TextView subjectNameTv;

    private List<User> studentList = new ArrayList<>();

    private int groupId;
    private int teacherId;
    private int classId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle b) {
        super.onViewCreated(v,b);
        
        if (getArguments() != null) {
            groupId = getArguments().getInt("groupId", -1);
        }
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        teacherId = prefs.getInt("userId", -1);

        if (groupId<0 || teacherId<0) {
            Toast.makeText(getContext(),"Brak ID grupy albo nauczyciela",Toast.LENGTH_SHORT).show();
            return;
        }

        studentSpinner     = v.findViewById(R.id.studentsSpinner);
        gradeSpinner       = v.findViewById(R.id.gradesSpinner);
        descriptionEditText= v.findViewById(R.id.editTextTextMultiLine);
        subjectNameTv      = v.findViewById(R.id.subjectNameTv);
        Button addBtn      = v.findViewById(R.id.addGradeButton);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"1","2","3","4","5","6"}
        );
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        fetchStudentsFromGroup(groupId);
        fetchSubjectForGroupAndTeacher(groupId, teacherId);

        addBtn.setOnClickListener(__ -> {
            int stuPos = studentSpinner.getSelectedItemPosition();
            if (stuPos<0 || stuPos>=studentList.size()) {
                Toast.makeText(getContext(),"Wybierz ucznia",Toast.LENGTH_SHORT).show();
                return;
            }
            User selectedStudent = studentList.get(stuPos);
            int gradeValue = Integer.parseInt((String)gradeSpinner.getSelectedItem());
            String desc = descriptionEditText.getText().toString();

            Map<String,Object> body = new HashMap<>();
            body.put("grade",       gradeValue);
            body.put("description", desc);
            body.put("studentId",   selectedStudent.getId());
            body.put("teacherId",   teacherId);
            body.put("classId",     classId);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GradeApi gradeApi = retrofit.create(GradeApi.class);
            gradeApi.addGrade(body).enqueue(new Callback<Void>() {
                @Override public void onResponse(Call<Void> c, Response<Void> r) {
                    if (r.isSuccessful()) {
                        Toast.makeText(getContext(),"Dodano ocenę",Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new GradesFragment())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(getContext(),"Błąd: "+r.code(),Toast.LENGTH_SHORT).show();
                    }
                }
                @Override public void onFailure(Call<Void> c, Throwable t) {
                    Toast.makeText(getContext(),"Błąd sieci: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void fetchStudentsFromGroup(int groupId) {
        Retrofit r = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = r.create(UserApi.class);
        userApi.getStudentsByGroup(groupId)
                .enqueue(new Callback<List<User>>() {
                    @Override public void onResponse(Call<List<User>> c, Response<List<User>> r) {
                        if (r.isSuccessful() && r.body()!=null) {
                            studentList = r.body();
                            List<String> names = new ArrayList<>();
                            for (User u: studentList) {
                                names.add(u.getName()+" "+u.getSurname());
                            }
                            ArrayAdapter<String> a = new ArrayAdapter<>(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    names);
                            a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            studentSpinner.setAdapter(a);
                        }
                    }
                    @Override public void onFailure(Call<List<User>> c, Throwable t) {
                        Toast.makeText(getContext(),"Błąd sieci: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchSubjectForGroupAndTeacher(int groupId, int teacherId) {
        Retrofit r = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GroupApi gApi = r.create(GroupApi.class);
        gApi.getSubjectForGroupAndTeacher(groupId, teacherId)
                .enqueue(new Callback<ClassDTO>() {
                    @Override public void onResponse(Call<ClassDTO> c, Response<ClassDTO> r) {
                        if (r.isSuccessful() && r.body()!=null) {
                            classId = r.body().getId();
                            subjectNameTv.setText(r.body().getClassName());
                        } else {
                            Toast.makeText(getContext(),"Brak przedmiotu",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<ClassDTO> c, Throwable t) {
                        Toast.makeText(getContext(),"Błąd sieci: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
