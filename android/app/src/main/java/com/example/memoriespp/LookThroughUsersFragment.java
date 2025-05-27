package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

/**
 * Fragment odpowiedzialny za wyświetlanie i filtrowanie listy użytkowników.
 * Umożliwia wyszukiwanie użytkowników po imieniu i nazwisku, oraz ich edycję
 * poprzez długie kliknięcie i wybór opcji z menu kontekstowego.
 */
public class LookThroughUsersFragment extends Fragment {

    private SearchView searchView;
    private Spinner roleSpinner, groupSpinner;
    private LinearLayout usersContainer;
    private UserApi userApi;

    private List<UserResponse> allUsers = new ArrayList<>();

    /**
     * Inicjalizuje widok fragmentu, ustawia API Retrofit oraz rejestruje
     * listener dla SearchView. Ładuje listę wszystkich użytkowników.
     *
     * @param inflater  obiekt LayoutInflater do tworzenia widoku
     * @param container kontener-rodzic
     * @param savedInstanceState poprzedni stan (jeśli istnieje)
     * @return widok fragmentu
     */
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

    /**
     * Pobiera listę wszystkich użytkowników z backendu
     * za pomocą interfejsu UserApi i wyświetla je w widoku.
     */
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

    /**
     * Wyświetla użytkowników w kontenerze z możliwością edycji przez długie kliknięcie.
     *
     * @param list lista użytkowników do wyświetlenia
     */
    private void displayUsers(List<UserResponse> list) {
        usersContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (UserResponse user : list) {
            View item = inflater.inflate(R.layout.item_user, usersContainer, false);
            TextView tv = item.findViewById(R.id.userNameTv);
            tv.setText(user.getName() + " " + user.getSurname());

            item.setOnLongClickListener(v -> {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenu().add("Edycja");
                popup.setOnMenuItemClickListener(mi -> {
                    if ("Edycja".equals(mi.getTitle().toString())) {
                        EditUserFragment frag = new EditUserFragment();
                        Bundle args = new Bundle();
                        args.putInt("userId", user.getId());
                        frag.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, frag)
                                .addToBackStack(null)
                                .commit();
                    }
                    return true;
                });
                popup.show();
                return true;
            });

            usersContainer.addView(item);
        }
    }

    /**
     * Filtrowanie listy użytkowników na podstawie wpisanego tekstu.
     *
     * @param query ciąg znaków do wyszukania (ignoruje wielkość liter)
     */
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
