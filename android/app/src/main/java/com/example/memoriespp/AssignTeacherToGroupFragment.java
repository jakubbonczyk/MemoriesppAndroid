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

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.UserApi;
import network.GroupApi;
import network.UserResponse;
import network.GroupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignTeacherToGroupFragment extends Fragment {

    private Spinner chooseTeacherSpinner;
    private LinearLayout groupsContainer;
    private UserApi userApi;
    private GroupApi groupApi;

    private List<UserResponse> teacherList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(
                R.layout.fragment_assign_teacher_to_groups,
                container,
                false);

        chooseTeacherSpinner = root.findViewById(R.id.chooseTeacherSpinner);
        groupsContainer      = root.findViewById(R.id.groupsContainer);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi  = retrofit.create(UserApi.class);
        groupApi = retrofit.create(GroupApi.class);

        loadTeachers();

        chooseTeacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int userId = teacherList.get(pos).getId();
                loadGroupsForUser(userId);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        return root;
    }

    private void loadTeachers() {
        userApi.getAllTeachers().enqueue(new Callback<List<UserResponse>>() {
            @Override public void onResponse(Call<List<UserResponse>> call,
                                             Response<List<UserResponse>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    teacherList = res.body();
                    List<String> names = new ArrayList<>();
                    for (UserResponse u : teacherList) {
                        names.add(u.getName() + " " + u.getSurname());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            names
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    chooseTeacherSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania nauczycieli: " + res.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadGroupsForUser(int userId) {
        groupApi.getGroupsForUser(userId).enqueue(new Callback<List<GroupResponse>>() {
            @Override public void onResponse(Call<List<GroupResponse>> call,
                                             Response<List<GroupResponse>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    populateGroupRows(res.body());
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania grup: " + res.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateGroupRows(List<GroupResponse> groups) {
        groupsContainer.removeAllViews();
        LayoutInflater inf = LayoutInflater.from(getContext());
        for (GroupResponse g : groups) {
            View row = inf.inflate(
                    R.layout.item_group_of_teacher,
                    groupsContainer,
                    false);
            TextView txt = row.findViewById(R.id.groupNameTv);
            txt.setText(g.getGroupName());
            groupsContainer.addView(row);
        }
    }
}
