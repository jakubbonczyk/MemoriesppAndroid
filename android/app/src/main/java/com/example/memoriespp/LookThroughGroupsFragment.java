package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.GroupApi;
import network.GroupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Fragment odpowiedzialny za przeglądanie listy wszystkich grup
 * dostępnych w systemie. Pozwala również na filtrowanie grup
 * za pomocą pola wyszukiwania.
 */
public class LookThroughGroupsFragment extends Fragment {

    private SearchView searchView;
    private LinearLayout groupsContainer;
    private GroupApi groupApi;
    private List<GroupResponse> allGroups = new ArrayList<>();

    /**
     * Tworzy widok fragmentu oraz inicjalizuje komponenty
     * i zapytanie do API w celu pobrania listy grup.
     *
     * @param inflater LayoutInflater do tworzenia widoku
     * @param container kontener-rodzic widoku
     * @param savedInstanceState zapisany stan (jeśli istnieje)
     * @return widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(
                R.layout.fragment_look_through_groups,
                container,
                false);

        searchView      = root.findViewById(R.id.searchView);
        groupsContainer = root.findViewById(R.id.groupsContainer);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        groupApi = retrofit.create(GroupApi.class);

        loadGroups();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterGroups(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterGroups(newText);
                return true;
            }
        });

        return root;
    }

    /**
     * Wysyła zapytanie HTTP do backendu w celu pobrania listy
     * wszystkich grup. Wynik jest wyświetlany w widoku lub
     * w przypadku błędu pokazuje komunikat.
     */
    private void loadGroups() {
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(Call<List<GroupResponse>> call,
                                   Response<List<GroupResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allGroups = response.body();
                    displayGroups(allGroups);
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

    /**
     * Wyświetla przekazaną listę grup w kontenerze layoutu.
     *
     * @param list lista grup do wyświetlenia
     */
    private void displayGroups(List<GroupResponse> list) {
        groupsContainer.removeAllViews();
        LayoutInflater inf = LayoutInflater.from(getContext());
        for (GroupResponse g : list) {
            View item = inf.inflate(R.layout.item_group, groupsContainer, false);
            TextView tv = item.findViewById(R.id.groupNameTv);
            tv.setText(g.getGroupName());
            groupsContainer.addView(item);
        }
    }

    /**
     * Filtruje listę grup na podstawie tekstu wpisanego w SearchView
     * i aktualizuje widok wyświetlanych wyników.
     *
     * @param query tekst do filtrowania (wielkość liter ignorowana)
     */
    private void filterGroups(String query) {
        String lower = query.toLowerCase().trim();
        List<GroupResponse> filtered = new ArrayList<>();
        for (GroupResponse g : allGroups) {
            if (g.getGroupName().toLowerCase().contains(lower)) {
                filtered.add(g);
            }
        }
        displayGroups(filtered);
    }
}
