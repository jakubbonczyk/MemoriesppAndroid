package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        AppCompatButton sendNewPasswordButton = findViewById(R.id.button4);

        sendNewPasswordButton.setOnClickListener(view -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);

            Toast.makeText(ResetPasswordActivity.this,"Nowe hasło zostało wysłane!", Toast.LENGTH_SHORT).show();
        });
    }
}