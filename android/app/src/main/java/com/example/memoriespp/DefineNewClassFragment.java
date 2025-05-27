package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import network.ClassApi;
import network.CreateClassRequest;
import network.ClassResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment odpowiedzialny za definiowanie nowego przedmiotu (klasy).
 * Umożliwia administratorowi wprowadzenie nazwy i dodanie nowego wpisu
 * do bazy danych poprzez żądanie HTTP.
 */
public class DefineNewClassFragment extends Fragment {

    private EditText classInput;
    private ClassApi classApi;

    /**
     * Tworzy i zwraca widok fragmentu, inicjalizuje pola interfejsu i klienta API.
     *
     * @param inflater obiekt do "nadmuchania" widoku
     * @param container kontener widoku nadrzędnego
     * @param savedInstanceState zapisany stan instancji (jeśli istnieje)
     * @return widok główny fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_define_new_class,
                container,
                false);

        classInput = root.findViewById(R.id.classInput);
        AppCompatButton addBtn = root.findViewById(R.id.addNewClassButton);

        HttpLoggingInterceptor log = new HttpLoggingInterceptor(msg ->
                android.util.Log.d("HTTP", msg));
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(log)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        classApi = retrofit.create(ClassApi.class);

        addBtn.setOnClickListener(v -> defineNewClass());

        return root;
    }

    /**
     * Obsługuje akcję dodania nowego przedmiotu.
     * Weryfikuje poprawność danych, wysyła żądanie HTTP do API,
     * a po sukcesie informuje użytkownika i przekierowuje z powrotem.
     */
    private void defineNewClass() {
        String name = classInput.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(),
                    "Podaj nazwę przedmiotu",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        CreateClassRequest req = new CreateClassRequest(name);
        classApi.createClass(req).enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> res) {
                if (res.isSuccessful() && res.body() != null) {
                    Toast.makeText(getContext(),
                            "Przedmiot „" + res.body().getClassName()
                                    + "” dodany (id=" + res.body().getId() + ")",
                            Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AdminClassesFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    String err = res.errorBody() != null
                            ? res.errorBody().toString()
                            : "kod " + res.code();
                    Toast.makeText(getContext(),
                            "Błąd serwera: " + err,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Błąd sieci: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
