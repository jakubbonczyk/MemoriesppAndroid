package com.example.memoriespp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import network.GroupApi;
import network.GroupDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectGroupGradesFragment extends Fragment {
    private LinearLayout groupContainer;
    private GroupApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_group_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupContainer = view.findViewById(R.id.groupContainer);
        groupContainer.removeAllViews();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(GroupApi.class);

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(getContext(), "Brak zalogowanego użytkownika!", Toast.LENGTH_SHORT).show();
            return;
        }

        api.getGroupsForTeacher(userId).enqueue(new Callback<List<GroupDTO>>() {
            @Override
            public void onResponse(Call<List<GroupDTO>> call, Response<List<GroupDTO>> res) {
                if (!res.isSuccessful() || res.body() == null || res.body().isEmpty()) {
                    Toast.makeText(getContext(), "Brak grup lub błąd serwera", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater inflater = LayoutInflater.from(getContext());
                for (GroupDTO g : res.body()) {
                    View item = inflater.inflate(R.layout.item_group, groupContainer, false);
                    TextView tv = item.findViewById(R.id.tvGroupName);
                    tv.setText(g.getGroupName());
                    item.setOnClickListener(v -> {
                        AddGradesFragment f = new AddGradesFragment();
                        Bundle args = new Bundle();
                        args.putInt("groupId", g.getId());
                        f.setArguments(args);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, f)
                                .addToBackStack(null)
                                .commit();
                    });
                    groupContainer.addView(item);
                }
            }

            @Override
            public void onFailure(Call<List<GroupDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
