package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.GroupApi;
import network.UserResponse;
import network.ClassApi;
import network.GroupResponse;
import network.ClassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignTeacherToClassFragment extends Fragment {

    private Spinner chooseGroupSpinner;
    private LinearLayout teachersContainer;
    private GroupApi groupApi;
    private ClassApi classApi;

    private List<GroupResponse> groupList = new ArrayList<>();
    private List<ClassResponse>   classList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_assign_teacher_to_class,
                container,
                false);

        chooseGroupSpinner  = root.findViewById(R.id.chooseGroupSpinner);
        teachersContainer   = root.findViewById(R.id.teacherclassLayout);

        // Retrofit + API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        groupApi  = retrofit.create(GroupApi.class);
        classApi  = retrofit.create(ClassApi.class);

        // 1) załaduj listę przedmiotów
        loadClasses();

        // 2) załaduj listę grup
        loadGroups();

        // 3) gdy wybiorą grupę, załaduj nauczycieli
        chooseGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                int groupId = groupList.get(pos).getId();
                loadTeachersForGroup(groupId);
            }
            @Override public void onNothingSelected(AdapterView<?> p) { }
        });

        return root;
    }

    private void loadClasses() {
        classApi.getAllClasses().enqueue(new Callback<List<ClassResponse>>() {
            @Override public void onResponse(Call<List<ClassResponse>> c,
                                             Response<List<ClassResponse>> r) {
                if (r.isSuccessful() && r.body()!=null) {
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
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override public void onResponse(Call<List<GroupResponse>> c,
                                             Response<List<GroupResponse>> r) {
                if (r.isSuccessful() && r.body()!=null) {
                    groupList = r.body();
                    List<String> names = new ArrayList<>();
                    for (GroupResponse g:groupList) names.add(g.getGroupName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            names);
                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    chooseGroupSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd grup: "+r.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<List<GroupResponse>> c, Throwable t) {
                Toast.makeText(getContext(),
                        "Sieć grup: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadTeachersForGroup(int groupId) {
        groupApi.getTeachersByGroup(groupId).enqueue(new Callback<List<UserResponse>>() {
            @Override public void onResponse(Call<List<UserResponse>> c,
                                             Response<List<UserResponse>> r) {
                if (r.isSuccessful() && r.body()!=null) {
                    populateTeacherRows(r.body());
                } else {
                    Toast.makeText(getContext(),
                            "Błąd nauczycieli: "+r.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<List<UserResponse>> c, Throwable t) {
                Toast.makeText(getContext(),
                        "Sieć nauczycieli: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateTeacherRows(List<UserResponse> teachers) {
        teachersContainer.removeAllViews();
        LayoutInflater inf = LayoutInflater.from(getContext());

        // przygotuj listę nazw przedmiotów raz
        List<String> classNames = new ArrayList<>();
        for (ClassResponse cls : classList) {
            classNames.add(cls.getClassName());
        }

        for (UserResponse teacher : teachers) {
            View row = inf.inflate(
                    R.layout.item_teacher_class,
                    teachersContainer,
                    false);

            TextView nameTv = row.findViewById(R.id.teacherNameTv);
            Spinner subjSp  = row.findViewById(R.id.subjectSpinner);

            nameTv.setText(teacher.getName() + " " + teacher.getSurname());

            // wypełnij spinner przedmiotami:
            ArrayAdapter<String> subAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    classNames
            );
            subAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            subjSp.setAdapter(subAdapter);

            teachersContainer.addView(row);
        }
    }
}
