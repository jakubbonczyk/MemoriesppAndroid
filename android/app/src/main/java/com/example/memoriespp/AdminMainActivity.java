package com.example.memoriespp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AdminHomeFragment adminHomeFragment = new AdminHomeFragment();
    UsersFragment usersFragment = new UsersFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    TextView textViewName;
    TextView textViewRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminHomeFragment)
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, adminHomeFragment)
                            .commit();
                    return true;
                } else if (itemId == R.id.settings) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, settingsFragment)
                            .commit();
                    return true;
                } else if (itemId == R.id.users) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, usersFragment)
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
                case "A":
                    textViewRole.setText("Administrator");
                    break;
                default:
                    textViewRole.setText("Nieznana rola");
                    break;
            }
        }
    }
}
