package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.Map;

import network.EditUserRequest;
import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Fragment umożliwiający edycję danych użytkownika: imienia, nazwiska, roli i loginu (e-mail).
 * Umożliwia również ręczne zainicjowanie resetu hasła (obecnie symulowane tylko komunikatem).
 */
public class EditUserFragment extends Fragment {

    private Spinner roleSpinner;
    private EditText nameInput, surnameInput, emailInput;
    private AppCompatButton resetPwBtn, saveUserBtn;
    private UserApi userApi;
    private int userId;

    /**
     * Tworzy widok fragmentu, inicjalizuje komponenty UI oraz ustawia logikę
     * obsługi zapisu i resetu hasła.
     *
     * @param inflater obiekt służący do "nadmuchania" widoku z XML-a
     * @param container kontener, do którego zostanie dodany widok
     * @param savedInstanceState zapisany stan (jeśli dotyczy)
     * @return widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_edit_user,
                container,
                false
        );

        roleSpinner   = root.findViewById(R.id.spinner);
        nameInput     = root.findViewById(R.id.nameInput);
        surnameInput  = root.findViewById(R.id.surnameInput);
        emailInput    = root.findViewById(R.id.emailInput);
        resetPwBtn    = root.findViewById(R.id.resetPasswordButton);
        saveUserBtn   = root.findViewById(R.id.saveUserButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class);

        userId = requireArguments().getInt("userId");
        loadUser(userId);

        saveUserBtn.setOnClickListener(v -> saveUser());

        resetPwBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                            "Zresetowano hasło",
                            Toast.LENGTH_SHORT)
                    .show();
        });

        return root;
    }

    /**
     * Ładuje dane użytkownika z API na podstawie ID i wypełnia pola formularza.
     *
     * @param userId identyfikator użytkownika do edycji
     */
    private void loadUser(int userId) {
        userApi.getUserById(userId)
                .enqueue(new Callback<Map<String,String>>() {
                    @Override
                    public void onResponse(Call<Map<String,String>> call,
                                           Response<Map<String,String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Map<String,String> m = response.body();
                            nameInput.setText(m.get("name"));
                            surnameInput.setText(m.get("surname"));
                            emailInput.setText(m.get("login"));

                            String roleCode    = m.get("role");
                            String displayRole = "Uczniowie";
                            if ("A".equals(roleCode)) {
                                displayRole = "Admin";
                            } else if ("T".equals(roleCode)) {
                                displayRole = "Nauczyciele";
                            }

                            @SuppressWarnings("unchecked")
                            ArrayAdapter<String> adapter =
                                    (ArrayAdapter<String>) roleSpinner.getAdapter();
                            int pos = adapter.getPosition(displayRole);
                            if (pos >= 0) {
                                roleSpinner.setSelection(pos);
                            }
                        } else {
                            Toast.makeText(requireContext(),
                                            "Błąd pobierania użytkownika: " + response.code(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String,String>> call, Throwable t) {
                        Toast.makeText(requireContext(),
                                        "Błąd sieci: " + t.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    /**
     * Pobiera dane z formularza i wysyła żądanie aktualizacji użytkownika na serwerze.
     * Po pomyślnym zapisie cofa do poprzedniego fragmentu.
     */
    private void saveUser() {
        String name     = nameInput.getText().toString().trim();
        String surname  = surnameInput.getText().toString().trim();
        String login    = emailInput.getText().toString().trim();
        String roleDisp = roleSpinner.getSelectedItem().toString();

        String roleCode;
        if ("Admin".equals(roleDisp)) {
            roleCode = "A";
        } else if ("Nauczyciele".equals(roleDisp)) {
            roleCode = "T";
        } else {
            roleCode = "S";
        }

        EditUserRequest req = new EditUserRequest(
                login,
                name,
                surname,
                roleCode
        );

        userApi.updateUser(userId, req)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call,
                                           Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(),
                                            "Zapisano zmiany",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getContext(),
                                            "Błąd serwera: " + response.code(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(),
                                        "Błąd sieci przy zapisie: " + t.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
