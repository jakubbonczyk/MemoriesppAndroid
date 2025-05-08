package com.example.memoriespp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.Map;

import network.GradeApi;
import network.NewGradeResponse;
import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_NOTIFY = 1001;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "grades_channel",
                    "Powiadomienia o ocenach",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Kanał dla powiadomień o nowych ocenach");
            getSystemService(NotificationManager.class)
                    .createNotificationChannel(channel);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment)
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment target = null;
            if (id == R.id.home)       target = homeFragment;
            else if (id == R.id.settings) target = settingsFragment;
            else if (id == R.id.grades)   target = gradesFragment;
            if (target != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, target)
                        .commit();
                return true;
            }
            return false;
        });

        textViewName = findViewById(R.id.textView7);
        textViewRole = findViewById(R.id.textView8);

        String name    = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String role    = getIntent().getStringExtra("role");
        String image   = getIntent().getStringExtra("image");
        int    userId  = getIntent().getIntExtra("userId", -1);

        if (name != null && surname != null) {
            textViewName.setText(name + " " + surname);
        }

        if (role != null) {
            switch (role) {
                case "S": textViewRole.setText("Uczeń"); break;
                case "T": textViewRole.setText("Nauczyciel"); break;
                default:  textViewRole.setText("Nieznana rola"); break;
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString("role",  role);
        bundle.putString("image", image);
        bundle.putInt("userId", userId);

        settingsFragment.setArguments(bundle);
        homeFragment    .setArguments(bundle);
        gradesFragment  .setArguments(bundle);

        ImageView profileImageView = findViewById(R.id.imageView4);
        if (userId != -1) {
            fetchAndSetProfileImage(userId);
        }

        if (image != null && !image.isEmpty()) {
            byte[] decoded = Base64.decode(image, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
            profileImageView.setImageBitmap(bmp);
        } else {
            profileImageView.setImageResource(R.drawable.profpic);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForNewGrades();
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
        refreshProfileImage(b64);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
    }

    private void checkForNewGrades() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        String role  = prefs.getString("role", "");

        if (userId != -1 && "S".equals(role)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GradeApi api = retrofit.create(GradeApi.class);
            api.getNewGrades(userId).enqueue(new Callback<List<NewGradeResponse>>() {
                @Override
                public void onResponse(Call<List<NewGradeResponse>> call,
                                       Response<List<NewGradeResponse>> resp) {
                    if (resp.isSuccessful() && resp.body() != null) {
                        for (NewGradeResponse g : resp.body()) {
                            showNotification(g);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<NewGradeResponse>> call, Throwable t) {
                    Log.e("NOTIF", "Błąd pobierania nowych ocen: " + t.getMessage());
                }
            });
        }
    }
    private void showNotification(NewGradeResponse g) {
        // 1) od Android 13 w górę najpierw prośba o uprawnienie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{ android.Manifest.permission.POST_NOTIFICATIONS },
                        REQ_NOTIFY
                );
                return;
            }
        }

        // 2) budowa i wyświetlenie powiadomienia
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "grades_channel")
                .setSmallIcon(R.drawable.baseline_beenhere_24)
                .setContentTitle("Nowa ocena z " + g.getClassName())
                .setContentText(g.getType() + " – Ocena: " + g.getGrade())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(this)
                .notify(g.getId(), builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_NOTIFY &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // po przyznaniu uprawnienia, ponownie sprawdź nowe oceny
            checkForNewGrades();
        }
    }


}
