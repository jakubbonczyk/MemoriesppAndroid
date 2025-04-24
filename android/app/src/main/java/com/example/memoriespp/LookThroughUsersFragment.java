package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.UserApi;
import network.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LookThroughUsersFragment extends Fragment {

    private SearchView searchView;
    private Spinner roleSpinner, groupSpinner;
    private LinearLayout usersContainer;
    private UserApi userApi;

    private List<UserResponse> allUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_look_through_users, container, false);

        searchView      = root.findViewById(R.id.searchView);
        roleSpinner     = root.findViewById(R.id.spinner);
        groupSpinner    = root.findViewById(R.id.spinner2);
        usersContainer  = root.findViewById(R.id.usersContainer);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class);

        loadUsers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterUsers(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });

        return root;
    }

    private void loadUsers() {
        userApi.getAllUsers().enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call,
                                   Response<List<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allUsers = response.body();
                    displayUsers(allUsers);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania użytkowników: " + response.code(),
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

    private void displayUsers(List<UserResponse> list) {
        usersContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (UserResponse user : list) {
            View item = inflater.inflate(R.layout.item_user, usersContainer, false);
            TextView tv = item.findViewById(R.id.userNameTv);
            tv.setText(user.getName() + " " + user.getSurname());
            usersContainer.addView(item);
        }
    }

    private void filterUsers(String query) {
        String lower = query.toLowerCase();
        List<UserResponse> filtered = new ArrayList<>();
        for (UserResponse u : allUsers) {
            if ((u.getName() + " " + u.getSurname()).toLowerCase().contains(lower)) {
                filtered.add(u);
            }
        }
        displayUsers(filtered);
    }
}
