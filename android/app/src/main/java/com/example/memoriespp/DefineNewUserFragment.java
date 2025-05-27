package com.example.memoriespp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.AuthApi;
import network.GroupApi;
import network.GroupResponse;
import network.RegisterUserRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Fragment służący do tworzenia nowego użytkownika (ucznia, nauczyciela lub administratora).
 * Administrator może podać dane osobowe użytkownika, wybrać rolę oraz grupę,
 * a następnie utworzyć konto przez wysłanie żądania do API.
 */
public class DefineNewUserFragment extends Fragment {

    private Spinner roleSpinner, groupSpinner;
    private EditText nameInput, surnameInput, emailInput;
    private AuthApi authApi;
    private GroupApi groupApi;
    private List<GroupResponse> groupList = new ArrayList<>();

    /**
     * Inicjalizuje interfejs użytkownika, ładuje dane grup i przypisuje zdarzenia kliknięć.
     *
     * @param inflater inflater widoku
     * @param container kontener dla fragmentu
     * @param savedInstanceState stan zapisany (jeśli istnieje)
     * @return zainicjalizowany widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_define_new_user, container, false);

        roleSpinner  = rootView.findViewById(R.id.spinner);
        groupSpinner = rootView.findViewById(R.id.spinner2);
        nameInput    = rootView.findViewById(R.id.nameInput);
        surnameInput = rootView.findViewById(R.id.surnameInput);
        emailInput   = rootView.findViewById(R.id.emailInput);
        AppCompatButton addNewUserButton = rootView.findViewById(R.id.addNewUserButton);

        List<RoleItem> roles = List.of(
                new RoleItem("S", "Uczniowie"),
                new RoleItem("T", "Nauczyciele"),
                new RoleItem("A", "Administrator")
        );
        ArrayAdapter<RoleItem> roleAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                roles
        );
        roleAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(msg ->
                Log.d("HTTP", msg)
        );
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi  = retrofit.create(AuthApi.class);
        groupApi = retrofit.create(GroupApi.class);

        loadGroups();

        addNewUserButton.setOnClickListener(v -> defineNewUser());

        return rootView;
    }

    /**
     * Pobiera listę wszystkich dostępnych grup z backendu i ustawia je w spinnerze grup.
     */
    private void loadGroups() {
        groupApi.getAllGroups().enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(Call<List<GroupResponse>> call,
                                   Response<List<GroupResponse>> res) {
                if (res.isSuccessful() && res.body() != null) {
                    groupList = res.body();
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
                            android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(),
                            "Błąd pobierania grup: " + res.code(),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci przy pobieraniu grup: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Waliduje wprowadzone dane, tworzy obiekt `RegisterUserRequest`
     * i wysyła żądanie rejestracji użytkownika do API.
     * Obsługuje odpowiedzi błędne oraz komunikaty zwrotne.
     */
    private void defineNewUser() {
        String name    = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String email   = emailInput.getText().toString().trim();
        RoleItem sel   = (RoleItem) roleSpinner.getSelectedItem();
        String roleCode= sel.code;
        int pos = groupSpinner.getSelectedItemPosition();
        Integer groupId = pos >= 0 && pos < groupList.size()
                ? groupList.get(pos).getId()
                : null;

        if (name.isEmpty() || surname.isEmpty()
                || email.isEmpty() || groupId == null) {
            Toast.makeText(getContext(),
                    "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            return;
        }

        String login    = email;
        String password = "test123";

        RegisterUserRequest req = new RegisterUserRequest(
                login,
                password,
                name,
                surname,
                roleCode,
                groupId
        );

        authApi.registerUser(req).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> c, Response<String> r) {
                Log.d("REGISTER", "code=" + r.code()
                        + ", successful=" + r.isSuccessful());
                if (!r.isSuccessful()) {
                    String err;
                    try {
                        err = r.errorBody() != null
                                ? r.errorBody().string()
                                : "brak ciała odpowiedzi";
                    } catch (IOException e) {
                        err = "błąd czytania errorBody: " + e.getMessage();
                    }
                    Log.e("REGISTER", "Server error: " + err);
                    Toast.makeText(getContext(),
                            "Błąd serwera: " + r.code() + "\n" + err,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getContext(),
                        "Użytkownik utworzony", Toast.LENGTH_SHORT).show();
                UsersFragment usersFragment = new UsersFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, usersFragment)
                        .commit();
            }
            @Override
            public void onFailure(Call<String> c, Throwable t) {
                Log.e("REGISTER", "network failure", t);
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Pomocnicza klasa reprezentująca rolę użytkownika w spinnerze.
     * Zawiera kod roli ("S", "T", "A") oraz nazwę wyświetlaną.
     */
    private static class RoleItem {
        final String code, name;
        RoleItem(String code, String name) {
            this.code = code;
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }
}
