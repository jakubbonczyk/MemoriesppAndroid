package com.example.memoriespp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsFragment extends Fragment {

    // ───────────────────────────────  stałe / pola  ──────────────────────────────
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private int       userId = -1;           // pamiętamy ID zalogowanego

    // ───────────────────────────────  LIFE‑CYCLE  ───────────────────────────────
    @Override public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle s) {

        View root = inf.inflate(R.layout.fragment_settings, c, false);

        /* ---------- widoki ---------- */
        profileImageView      = root.findViewById(R.id.profileImage);
        Button changePicBtn   = root.findViewById(R.id.changeProfilePictureButton);
        Button logoutBtn      = root.findViewById(R.id.logoutButton);
        Button changeLangBtn  = root.findViewById(R.id.changeLanguageButton);

        /* ---------- akcje ---------- */
        logoutBtn.setOnClickListener(v ->
                Toast.makeText(getContext(), "Poprawnie wylogowano", Toast.LENGTH_SHORT).show());

        changeLangBtn.setOnClickListener(v -> {
            String newL = Locale.getDefault().getLanguage().equals("pl") ? "en" : "pl";
            LocaleHelper.changeLanguage(requireContext(), newL);
            requireActivity().recreate();
        });

        changePicBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK).setType("image/*");
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        });

        /* ---------- dane startowe ---------- */
        if (getActivity() != null)
            userId = getActivity().getIntent().getIntExtra("userId", -1);

        // obraz przekazany z LoginActivity
        Bundle args = getArguments();
        if (args != null) {
            showBase64Image(args.getString("image"));
        }

        // dociągamy na świeżo z backendu
        if (userId != -1) fetchLatestProfileImage(userId);

        return root;
    }

    @Override public void onResume() {
        super.onResume();
        // w razie powrotu do fragmentu
        if (userId != -1) fetchLatestProfileImage(userId);
    }

    // ───────────────────────────────  WYBÓR ZDJĘCIA  ─────────────────────────────
    @Override public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == PICK_IMAGE_REQUEST && res == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try (InputStream is = requireContext().getContentResolver().openInputStream(uri)) {

                Bitmap bmp = BitmapFactory.decodeStream(is);
                profileImageView.setImageBitmap(bmp);

                String b64 = bitmapToBase64(bmp);

                propagateImageToHost(b64);          // Main + Admin
                sendImageToBackend(b64);            // upload

            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    // ───────────────────────────────  BACKEND  ───────────────────────────────────
    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void sendImageToBackend(String b64) {
        Map<String,String> body = new HashMap<>();
        body.put("image", b64);

        retrofit().create(UserApi.class)
                .uploadProfileImage(userId, body)
                .enqueue(new Callback<Void>() {
                    public void onResponse(Call<Void> c, Response<Void> r) {

                        if (!r.isSuccessful()){
                            Toast.makeText(getContext(),
                                    "Błąd aktualizacji zdjęcia", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fetchLatestProfileImage(userId);
                    }

                    public void onFailure(Call<Void> c, Throwable t){
                        Toast.makeText(getContext(),
                                "Błąd połączenia", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchLatestProfileImage(int id) {

        retrofit().create(UserApi.class)
                .getUserById(id)
                .enqueue(new Callback<Map<String,String>>() {
                    public void onResponse(Call<Map<String,String>> c, Response<Map<String,String>> r) {

                        if (!r.isSuccessful() || r.body()==null) return;

                        String b64 = r.body().get("image");
                        showBase64Image(b64);
                        propagateImageToHost(b64);      // Main + Admin
                    }
                    public void onFailure(Call<Map<String,String>> c, Throwable t) {
                        Log.e("FETCH","Nie udało się pobrać zdjęcia: "+t.getMessage());
                    }
                });
    }

    // ───────────────────────────────  POMOCNICZE  ───────────────────────────────
    /** Ustawia grafikę w ImageView z ciągu Base‑64 lub domyślne. */
    private void showBase64Image(String b64){
        if (b64!=null && !b64.isEmpty())
            profileImageView.setImageBitmap(base64ToBitmap(b64));
        else
            profileImageView.setImageResource(R.drawable.profpic);
    }

    /** Zamienia Bitmap → Base64 (NO_WRAP). */
    private static String bitmapToBase64(Bitmap b){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,100,baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
    }

    private static Bitmap base64ToBitmap(String b64){
        byte[] bytes = Base64.decode(b64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    /** Informuje aktywność (Main lub AdminMain), by podmieniła swój avatar. */
    private void propagateImageToHost(String b64){
        if (getActivity() instanceof MainActivity)
            ((MainActivity)getActivity()).refreshProfileImage(b64);
        else if (getActivity() instanceof AdminMainActivity)
            ((AdminMainActivity)getActivity()).refreshProfileImage(b64);
    }

    /* ───────────────────────────────  LOCALE helper  ───────────────────────────
       (zostawiam – może się jeszcze przydać)                                   */
    private void setLocale(String lang){
        Locale locale = new Locale(lang);  Locale.setDefault(locale);
        Configuration cfg = new Configuration(); cfg.setLocale(locale);
        getResources().updateConfiguration(cfg, getResources().getDisplayMetrics());
    }
}
