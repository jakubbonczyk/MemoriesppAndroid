package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import network.CreateGroupRequest;
import network.GroupApi;
import network.GroupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment służący do tworzenia nowych grup użytkowników w systemie.
 * Umożliwia administratorowi wpisanie nazwy grupy i przesłanie jej do backendu.
 */
public class DefineNewGroupFragment extends Fragment {

    private EditText groupNameInput;
    private GroupApi groupApi;

    /**
     * Inicjalizuje widok fragmentu i ustawia akcję przycisku do tworzenia nowej grupy.
     *
     * @param inflater obiekt do tworzenia widoku
     * @param container kontener, w którym znajduje się fragment
     * @param savedInstanceState stan zapisany (jeśli istnieje)
     * @return główny widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_define_new_group, container, false);

        groupNameInput    = rootView.findViewById(R.id.emailInput);
        AppCompatButton addNewGroupButton = rootView.findViewById(R.id.addNewGroupButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        groupApi = retrofit.create(GroupApi.class);

        addNewGroupButton.setOnClickListener(v -> defineNewGroup());

        return rootView;
    }

    /**
     * Obsługuje logikę tworzenia nowej grupy:
     * sprawdza poprawność danych wejściowych, wysyła żądanie POST do API,
     * a w przypadku sukcesu przekierowuje z powrotem do ekranu zarządzania grupami.
     */
    private void defineNewGroup() {
        String name = groupNameInput.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Podaj nazwę grupy", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateGroupRequest req = new CreateGroupRequest(name);
        groupApi.createGroup(req).enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> res) {
                if (res.isSuccessful() && res.body() != null) {
                    Toast.makeText(getContext(),
                            "Grupa „" + res.body().getGroupName() + "” została utworzona",
                            Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AdminGroupsFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(),
                            "Błąd serwera: " + res.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
