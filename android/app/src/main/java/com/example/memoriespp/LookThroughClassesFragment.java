package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import network.ClassApi;
import network.ClassResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Fragment umożliwiający przeglądanie oraz filtrowanie listy wszystkich przedmiotów
 * (klas) pobranych z serwera.
 * Używany przez administratora do podglądu istniejących wpisów.
 */
public class LookThroughClassesFragment extends Fragment {

    private SearchView searchView;
    private LinearLayout classesContainer;
    private ClassApi classApi;
    private List<ClassResponse> classList = new ArrayList<>();

    /**
     * Tworzy widok fragmentu i inicjalizuje elementy interfejsu.
     *
     * @param inflater używany do "napompowania" layoutu XML
     * @param container widok-rodzic
     * @param savedInstanceState stan zapisany (jeśli istnieje)
     * @return korzeń hierarchii widoku
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_look_through_classes, container, false);

        searchView        = root.findViewById(R.id.searchView);
        classesContainer  = root.findViewById(R.id.classesContainer);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classApi = retrofit.create(ClassApi.class);

        loadClasses();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterClasses(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterClasses(newText);
                return true;
            }
        });

        return root;
    }

    /**
     * Pobiera wszystkie przedmioty z API i wyświetla je w kontenerze.
     */
    private void loadClasses() {
        classApi.getAllClasses().enqueue(new Callback<List<ClassResponse>>() {
            @Override
            public void onResponse(Call<List<ClassResponse>> call,
                                   Response<List<ClassResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    classList = response.body();
                    displayClasses(classList);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania przedmiotów: " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<ClassResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Wyświetla listę przedmiotów w widoku.
     *
     * @param list lista przedmiotów do wyświetlenia
     */
    private void displayClasses(List<ClassResponse> list) {
        classesContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (ClassResponse cls : list) {
            View item = inflater.inflate(R.layout.item_class, classesContainer, false);
            TextView tv = item.findViewById(R.id.classNameTv);
            tv.setText(cls.getClassName());
            classesContainer.addView(item);
        }
    }

    /**
     * Filtruje listę przedmiotów na podstawie tekstu wyszukiwania.
     *
     * @param query tekst wpisany przez użytkownika
     */
    private void filterClasses(String query) {
        String lower = query.toLowerCase();
        List<ClassResponse> filtered = new ArrayList<>();
        for (ClassResponse cls : classList) {
            if (cls.getClassName().toLowerCase().contains(lower)) {
                filtered.add(cls);
            }
        }
        displayClasses(filtered);
    }
}
