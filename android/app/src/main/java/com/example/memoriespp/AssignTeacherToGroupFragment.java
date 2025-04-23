package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.GroupApi;
import network.GroupResponse;
import network.UserApi;
import network.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AssignTeacherToGroupFragment extends Fragment {

    private Spinner chooseTeacherSpinner;
    private LinearLayout groupsContainer;
    private AppCompatButton addAnotherGroupBtn, saveBtn;

    private GroupApi groupApi;
    private UserApi userApi;
    private List<UserResponse> teacherList = new ArrayList<>();
    private List<GroupResponse> currentGroups = new ArrayList<>();
    private List<GroupResponse> allGroups     = new ArrayList<>();

    private int selectedTeacherId;  // <— tu przechowujemy ID wybranego nauczyciela

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_assign_teacher_to_groups,
                container,
                false);

        chooseTeacherSpinner   = root.findViewById(R.id.chooseTeacherSpinner);
        groupsContainer        = root.findViewById(R.id.groupsContainer);
        addAnotherGroupBtn     = root.findViewById(R.id.addAnotherGroupButton);
        saveBtn                = root.findViewById(R.id.addNewClassButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                // najpierw scalars, żeby obsłużyć plain-text odpowiedzi typu "OK"
                .addConverterFactory(ScalarsConverterFactory.create())
                // potem JSON dla innych endpointów
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi  = retrofit.create(UserApi.class);
        groupApi = retrofit.create(GroupApi.class);

        // 1) Załaduj WSZYSTKIE grupy do allGroups (do popupa)
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(Call<List<GroupResponse>> call,
                                   Response<List<GroupResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allGroups = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                // opcjonalnie loguj błąd
            }
        });

        // 2) Załaduj listę nauczycieli
        loadTeachers();

        // 3) Po wybraniu nauczyciela pobierz jego grupy
        chooseTeacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int pos,
                                       long id) {
                selectedTeacherId = teacherList.get(pos).getId();
                loadGroupsForUser(selectedTeacherId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 4) Popup „Dodaj kolejną grupę”
        addAnotherGroupBtn.setOnClickListener(v -> showAddGroupPopup());


        // 5) (opcjonalnie) przycisk Zapisz – tutaj możesz zrobić dodatkowe potwierdzenie
        saveBtn.setOnClickListener(v ->
                Toast.makeText(getContext(), "Zapisałem wszystkie zmiany", Toast.LENGTH_SHORT).show()
        );

        return root;
    }

    private void loadTeachers() {
        userApi.getAllTeachers().enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call,
                                   Response<List<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    teacherList = response.body();
                    List<String> names = new ArrayList<>();
                    for (UserResponse u : teacherList) {
                        names.add(u.getName() + " " + u.getSurname());
                    }
                    ArrayAdapter<String> ad = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            names
                    );
                    ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    chooseTeacherSpinner.setAdapter(ad);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania nauczycieli: " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadGroupsForUser(int userId) {
        groupApi.getGroupsForUser(userId).enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(Call<List<GroupResponse>> call,
                                   Response<List<GroupResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentGroups = response.body();
                    populateGroupRows(currentGroups);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania grup: " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
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

    private void showAddGroupPopup() {
        // 1) Oblicz grupy do dodania
        List<GroupResponse> toAdd = new ArrayList<>();
        for (GroupResponse g : allGroups) {
            boolean already = false;
            for (GroupResponse cg : currentGroups) {
                if (cg.getId().equals(g.getId())) { already = true; break; }
            }
            if (!already) toAdd.add(g);
        }

        // 2) Jeśli pusto – pokaż komunikat
        if (toAdd.isEmpty()) {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Dodaj grupę")
                    .setMessage("Ten użytkownik ma już przypisane wszystkie grupy.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // 3) Przygotuj tablicę nazw
        String[] names = new String[toAdd.size()];
        for (int i = 0; i < toAdd.size(); i++) {
            names[i] = toAdd.get(i).getGroupName();
        }

        // 4) AlertDialog z listą do wyboru
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Dodaj grupę")
                .setItems(names, (dialog, which) -> {
                    GroupResponse chosen = toAdd.get(which);
                    // wywołaj endpoint przypisania
                    groupApi.assignUserToGroup(selectedTeacherId, chosen.getId())
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> res) {
                                    if (res.isSuccessful()) {
                                        Toast.makeText(requireContext(),
                                                "Dodano do grupy „" + chosen.getGroupName() + "”",
                                                Toast.LENGTH_SHORT).show();
                                        loadGroupsForUser(selectedTeacherId);
                                    } else {
                                        Toast.makeText(requireContext(),
                                                "Błąd serwera: " + res.code(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(requireContext(),
                                            "Błąd sieci: " + t.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

}
