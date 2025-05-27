package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.HashMap;
import java.util.Map;

import network.AuthApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Aktywność odpowiedzialna za resetowanie hasła użytkownika.
 * Użytkownik wprowadza swój adres e-mail, a aplikacja wysyła żądanie
 * na backend o wygenerowanie tymczasowego hasła lub linku resetującego.
 * Jeśli operacja zakończy się sukcesem, użytkownik zostaje przekierowany
 * z powrotem do ekranu logowania.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailField;
    private AuthApi authApi;

    /**
     * Inicjalizuje widok, tworzy instancję Retrofit oraz ustawia listener
     * na przycisku wysyłającym żądanie resetu hasła.
     *
     * @param savedInstanceState zapisany stan aktywności (jeśli istnieje)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        AppCompatButton sendNewPasswordButton = findViewById(R.id.button4);
        emailField = findViewById(R.id.editTextText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApi = retrofit.create(AuthApi.class);

        sendNewPasswordButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";

            if (email.isEmpty()) {
                Toast.makeText(this, "Wprowadź adres e-mail", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.matches(emailRegex)) {
                Toast.makeText(this, "Niepoprawny format adresu e-mail", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> body = new HashMap<>();
            body.put("login", email);

            authApi.requestPasswordReset(body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("RESET", "Status: " + response.code());
                    if (response.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this,
                                "Jeśli konto istnieje, link resetujący został wysłany.",
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this,
                                "Nie udało się wysłać żądania. Spróbuj ponownie.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("RESET", "Błąd połączenia: " + t.getMessage());
                    Toast.makeText(ResetPasswordActivity.this,
                            "Błąd połączenia z serwerem", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
