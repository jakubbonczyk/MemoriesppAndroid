package com.example.memoriespp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Map;

import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    GradesFragment gradesFragment = new GradesFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    TextView textViewName;
    TextView textViewRole;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment)
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, homeFragment)
                            .commit();
                    return true;
                } else if (itemId == R.id.settings) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, settingsFragment)
                            .commit();
                    return true;
                } else if (itemId == R.id.grades) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, gradesFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });

        textViewName = findViewById(R.id.textView7);
        textViewRole = findViewById(R.id.textView8);

        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String role = getIntent().getStringExtra("role");

        Bundle bundle = new Bundle();
        bundle.putString("role", getIntent().getStringExtra("role"));
        bundle.putString("image", getIntent().getStringExtra("image"));

        settingsFragment.setArguments(bundle);
        homeFragment.setArguments(bundle);
        gradesFragment.setArguments(bundle);

        if (name != null && surname != null) {
            textViewName.setText(name + " " + surname);
        }

        if (role != null) {
            switch (role) {
                case "S":
                    textViewRole.setText("Uczeń");
                    break;
                case "T":
                    textViewRole.setText("Nauczyciel");
                    break;
                default:
                    textViewRole.setText("Nieznana rola");
                    break;
            }
        }

        ImageView profileImageView = findViewById(R.id.imageView4);
        int userId = getIntent().getIntExtra("userId", -1);
        if (userId != -1) {
            fetchAndSetProfileImage(userId);
        }

        String imageBase64 = getIntent().getStringExtra("image");

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            byte[] decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profileImageView.setImageBitmap(bitmap);
        } else {
            profileImageView.setImageResource(R.drawable.profpic); // domyślne
        }

    }

    public void refreshProfileImage(String base64Image) {
        ImageView profileImageView = findViewById(R.id.imageView4);
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profileImageView.setImageBitmap(bitmap);
        } else {
            profileImageView.setImageResource(R.drawable.profpic);
        }
    }

    private void fetchAndSetProfileImage(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi userApi = retrofit.create(UserApi.class);

        Call<Map<String, String>> call = userApi.getUserById(userId);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String base64Image = response.body().get("image");
                    ImageView profileImageView = findViewById(R.id.imageView4);
                    if (base64Image != null && !base64Image.isEmpty()) {
                        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        profileImageView.setImageBitmap(bitmap);
                    } else {
                        profileImageView.setImageResource(R.drawable.profpic);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("FETCH", "Błąd pobierania zdjęcia profilowego: " + t.getMessage());
            }
        });
    }

    public void updateProfileImageFromSettings(String base64Image) {
        ImageView profileImageView = findViewById(R.id.imageView4);
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profileImageView.setImageBitmap(bitmap);
        } else {
            profileImageView.setImageResource(R.drawable.profpic);
        }
    }

    public void onAvatarChanged(String b64){
        // 1. nagłówek
        refreshProfileImage(b64);

        // 2. jeśli HOME‑fragment już istnieje i implementuje interfejs
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
    }

}
