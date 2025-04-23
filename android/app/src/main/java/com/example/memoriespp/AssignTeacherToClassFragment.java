package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.GroupApi;
import network.GroupResponse;
import network.UserResponse;          // DTO dla użytkownika z backendu
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignTeacherToClassFragment extends Fragment {

    private Spinner chooseGroupSpinner;
    private LinearLayout teachersContainer;
    private GroupApi groupApi;

    private List<GroupResponse> groupList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_assign_teacher_to_class,
                container,
                false);

        chooseGroupSpinner  = root.findViewById(R.id.chooseGroupSpinner);
        teachersContainer  = root.findViewById(R.id.teacherclassLayout /* lub inny kontener */);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        groupApi = retrofit.create(GroupApi.class);

        // 1) załaduj grupy
        loadGroups();

        // 2) gdy wybiorą grupę, załaduj nauczycieli
        chooseGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int groupId = groupList.get(pos).getId();
                loadTeachersForGroup(groupId);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        return root;
    }

    private void loadGroups() {
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override public void onResponse(Call<List<GroupResponse>> call,
                                             Response<List<GroupResponse>> r) {
                if (r.isSuccessful() && r.body() != null) {
                    groupList = r.body();
                    List<String> names = new ArrayList<>();
                    for (GroupResponse g : groupList) {
                        names.add(g.getGroupName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            names
                    );
                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );
                    chooseGroupSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania grup: " + r.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci przy grupach: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadTeachersForGroup(int groupId) {
        // zakładam, że backend ma endpoint GET /api/groups/{id}/teachers
        groupApi.getTeachersByGroup(groupId).enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call,
                                   Response<List<UserResponse>> r) {
                if (r.isSuccessful() && r.body() != null) {
                    populateTeacherRows(r.body());
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania nauczycieli: " + r.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci przy nauczycielach: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateTeacherRows(List<UserResponse> teachers) {
        teachersContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (UserResponse teacher : teachers) {
            View row = inflater.inflate(R.layout.item_teacher_class,
                    teachersContainer,
                    false);
            TextView nameTv = row.findViewById(R.id.teacherNameTv);
            Spinner subjSp  = row.findViewById(R.id.subjectSpinner);

            nameTv.setText(teacher.getName() + " " + teacher.getSurname());
            // na razie zostaw subjSp pusty

            teachersContainer.addView(row);
        }
    }
}
