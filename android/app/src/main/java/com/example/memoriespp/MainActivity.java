package com.example.memoriespp;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
    }
}
