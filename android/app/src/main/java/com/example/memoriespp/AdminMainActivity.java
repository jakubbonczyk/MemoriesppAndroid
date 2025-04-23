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

public class AdminMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private final AdminHomeFragment adminHomeFragment = new AdminHomeFragment();
    private final UsersFragment      usersFragment     = new UsersFragment();
    private final SettingsFragment   settingsFragment  = new SettingsFragment();

    private TextView  textViewName;
    private TextView  textViewRole;
    private ImageView profileImageView;

    @Override protected void attachBaseContext(Context base){
        super.attachBaseContext(LocaleHelper.setLocale(base));
    }

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        profileImageView = findViewById(R.id.imageView4);
        textViewName     = findViewById(R.id.textView7);
        textViewRole     = findViewById(R.id.textView8);

        int    userId  = getIntent().getIntExtra("userId", -1);
        String name    = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String role    = getIntent().getStringExtra("role");
        String image64 = getIntent().getStringExtra("image");



        if (name != null)     textViewName.setText(name + " " + surname);
        if ("A".equals(role)) textViewRole.setText("Administrator");

        if (image64 != null && !image64.isEmpty()) {
            profileImageView.setImageBitmap(toBitmap(image64));
        } else {
            profileImageView.setImageResource(R.drawable.profpic);
        }

        if (userId != -1) refreshProfileImageFromServer(userId);

        Bundle b = new Bundle();
        b.putString("image", getIntent().getStringExtra("image"));
        settingsFragment.setArguments(b);
        adminHomeFragment.setArguments(b);
        usersFragment.setArguments(b);


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminHomeFragment)
                    .commit();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(MenuItem item) {

                    Fragment target;

                    int id = item.getItemId();
                    if (id == R.id.settings){
                        target = settingsFragment;
                    } else if (id == R.id.users){
                        target = usersFragment;
                    } else {
                        target = adminHomeFragment;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, target)
                            .commit();
                    return true;
                }
            };


    private void refreshProfileImageFromServer(int userId){
        new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi.class)
                .getUserById(userId)
                .enqueue(new Callback<Map<String,String>>() {
                    @Override public void onResponse(Call<Map<String,String>> c,
                                                     Response<Map<String,String>> r){
                        if (r.isSuccessful() && r.body()!=null){
                            String b64 = r.body().get("image");
                            refreshProfileImage(b64);
                        }
                    }
                    @Override public void onFailure(Call<Map<String,String>> c, Throwable t){
                        Log.e("ADMIN_FETCH", "foto err: "+t.getMessage());
                    }
                });
    }

    private static Bitmap toBitmap(String b64){
        byte[] bytes = Base64.decode(b64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    public void updateProfileImageFromSettings(String base64){
        refreshProfileImage(base64);
    }

    public void refreshProfileImage(String base64){
        ImageView iv = findViewById(R.id.imageView4);

        if (base64 != null && !base64.isEmpty()){
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bmp   = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            iv.setImageBitmap(bmp);
        } else {
            iv.setImageResource(R.drawable.profpic);
        }
    }

    public void onAvatarChanged(String b64){
        refreshProfileImage(b64);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
    }
}
