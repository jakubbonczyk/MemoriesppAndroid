package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import network.AuthApi;
import network.LoginResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatButton loginButton = findViewById(R.id.button3);
        AppCompatButton resetPasswordButton = findViewById(R.id.button4);
        AppCompatButton loginAsAdminButton = findViewById(R.id.loginAsAdminButton);

        EditText emailEditText = findViewById(R.id.editTextText);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080") // emulator Androida -> localhost
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
                        Toast.makeText(LoginActivity.this, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Błąd logowania", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Błąd połączenia: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });


        resetPasswordButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        loginAsAdminButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
        });
    }
}