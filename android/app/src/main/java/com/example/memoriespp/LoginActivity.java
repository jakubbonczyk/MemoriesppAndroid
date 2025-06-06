package com.example.memoriespp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import network.AuthApi;
import network.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Ekran logowania użytkownika do aplikacji Memories++.
 * Umożliwia podanie loginu i hasła, oraz przejście do formularza resetowania hasła.
 * W zależności od roli użytkownika uruchamia odpowiednią aktywność główną.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Metoda inicjalizująca widok logowania.
     * Obsługuje przyciski: zaloguj się oraz resetuj hasło.
     *
     * @param savedInstanceState zapisany stan (jeśli istniał)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatButton loginButton = findViewById(R.id.button3);
        AppCompatButton resetPasswordButton = findViewById(R.id.button4);

        EditText emailEditText = findViewById(R.id.editTextText);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthApi authApi = retrofit.create(AuthApi.class);

        loginButton.setOnClickListener(view -> {
            String login = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Map<String, String> credentials = new HashMap<>();
            credentials.put("login", login);
            credentials.put("password", password);

            Call<LoginResponse> call = authApi.login(credentials);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        String role = loginResponse.getRole();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userId", loginResponse.getId());
                        editor.putString("name", loginResponse.getName());
                        editor.putString("surname", loginResponse.getSurname());
                        editor.putString("role", loginResponse.getRole());
                        if ("S".equals(role)) {
                            editor.putString("studentName", loginResponse.getName() + " " + loginResponse.getSurname());
                            editor.putString("className", loginResponse.getClassName());
                        }


                        editor.apply();

                        Snackbar.make(view, "Zalogowano jako " + role, Snackbar.LENGTH_SHORT).show();

                        Intent intent;
                        switch (role) {
                            case "A":
                                intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                break;
                            case "T":
                            case "S":
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                break;
                            default:
                                Snackbar.make(view, "Nieznana rola użytkownika", Snackbar.LENGTH_LONG).show();
                                return;
                        }

                        intent.putExtra("userId", loginResponse.getId());
                        intent.putExtra("name", loginResponse.getName());
                        intent.putExtra("surname", loginResponse.getSurname());
                        intent.putExtra("role", loginResponse.getRole());

                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(view, "Błąd logowania", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Snackbar.make(view, "Błąd połączenia: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        });

        resetPasswordButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }
}
