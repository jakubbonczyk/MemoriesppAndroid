package com.example.memoriespp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import network.ClassResponse;
import network.GradeApi;
import network.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment ustawień użytkownika.
 *
 * Odpowiada za:
 * - zmianę motywu (jasny/ciemny),
 * - zmianę języka interfejsu,
 * - zarządzanie zdjęciem profilowym (podgląd, zmiana, zapis do backendu),
 * - wylogowywanie użytkownika,
 * - dostęp do ustawień powiadomień systemowych,
 * - generowanie PDF z ocenami ucznia.
 */
public class SettingsFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private int userId = -1;

    /**
     * Tworzy i konfiguruje główny widok fragmentu ustawień.
     */
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle s) {
        View root = inf.inflate(R.layout.fragment_settings, c, false);

        SharedPreferences themePrefs = requireActivity()
                .getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = themePrefs.getBoolean("dark_mode", false);

        Button changeThemeBtn = root.findViewById(R.id.changeThemeButton);

        changeThemeBtn.setText(
                isDarkMode ? R.string.theme_light : R.string.theme_dark
        );

        changeThemeBtn.setOnClickListener(v -> {
            boolean dark = !themePrefs.getBoolean("dark_mode", false);
            themePrefs.edit()
                    .putBoolean("dark_mode", dark)
                    .apply();
            AppCompatDelegate.setDefaultNightMode(
                    dark
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
            requireActivity().recreate();
        });

        profileImageView = root.findViewById(R.id.profileImage);
        Button changePicBtn = root.findViewById(R.id.changeProfilePictureButton);
        Button logoutBtn = root.findViewById(R.id.logoutButton);
        Button changeLangBtn = root.findViewById(R.id.changeLanguageButton);
        Button yourResultsButton = root.findViewById(R.id.yourResultsButton);

        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Poprawnie wylogowano", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        changeLangBtn.setOnClickListener(v -> {
            String newL = Locale.getDefault().getLanguage().equals("pl") ? "en" : "pl";
            LocaleHelper.changeLanguage(requireContext(), newL);
            requireActivity().recreate();
        });

        changePicBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK).setType("image/*");
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        });

        yourResultsButton.setOnClickListener(v -> {
            SharedPreferences prefs = requireActivity()
                    .getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
            int storedId = prefs.getInt("userId", -1);

            if (storedId != -1) {
                fetchAndGeneratePdf(storedId);
            } else {
                Toast.makeText(getContext(), "Brak ID użytkownika", Toast.LENGTH_SHORT).show();
            }
        });

        Button changeNotifBtn = root.findViewById(R.id.changeNotificationsSettingsButton);
        changeNotifBtn.setOnClickListener(v -> {
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().getPackageName());
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS")
                        .putExtra("app_package", requireContext().getPackageName())
                        .putExtra("app_uid", requireActivity().getApplicationInfo().uid);
            } else {
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + requireContext().getPackageName()));
            }
            startActivity(intent);
        });

        if (getActivity() != null)
            userId = getActivity().getIntent().getIntExtra("userId", -1);

        Bundle args = getArguments();
        if (args != null) {
            showBase64Image(args.getString("image"));
        }

        if (userId != -1) fetchLatestProfileImage(userId);

        return root;
    }

    /**
     * Pobiera dane ucznia i generuje dokument PDF z jego ocenami.
     */
    private void fetchAndGeneratePdf(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi api = retrofit.create(GradeApi.class);
        api.getSubjectsForStudent(userId).enqueue(new Callback<List<ClassResponse>>() {
            @Override
            public void onResponse(Call<List<ClassResponse>> call, Response<List<ClassResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
                    String studentName = prefs.getString("studentName", "Brak danych");
                    String className = prefs.getString("className", "Brak danych");

                    createPdf(response.body(), studentName, className);
                } else {
                    Toast.makeText(getContext(), "Błąd pobierania ocen", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<ClassResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Tworzy PDF z ocenami ucznia i otwiera go w przeglądarce PDF.
     */
    private void createPdf(List<ClassResponse> subjects, String studentName, String className) {
        PdfDocument pdf = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int y = 60;
        paint.setTextSize(20);
        paint.setFakeBoldText(true);
        canvas.drawText("Wykaz ocen", 240, y, paint);

        y += 30;
        paint.setTextSize(14);
        paint.setFakeBoldText(false);
        canvas.drawText("Uczeń: " + studentName, 60, y, paint);
        y += 20;
        canvas.drawText("Klasa: " + className, 60, y, paint);
        y += 30;

        for (ClassResponse subject : subjects) {
            String name = subject.getClassName();
            Double avg = subject.getAverage();
            String avgStr = avg != null ? getGradeDescription(avg) : "Brak danych";

            StringBuilder line = new StringBuilder(name);
            while (line.length() < 50) line.append(".");
            line.append(avgStr);

            canvas.drawText(line.toString(), 60, y, paint);
            y += 25;
        }

        y += 30;
        paint.setFakeBoldText(true);
        canvas.drawText("Średnia wszystkich ocen: " + calculateOverallAverage(subjects), 60, y, paint);

        pdf.finishPage(page);

        try {
            File baseDir = requireContext().getExternalFilesDir(null);
            File downloadDir = new File(baseDir, "Download");
            if (!downloadDir.exists()) downloadDir.mkdirs();

            File file = new File(downloadDir, "oceny.pdf");
            pdf.writeTo(new FileOutputStream(file));
            Toast.makeText(getContext(), "Zapisano PDF: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

            Uri uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.memoriespp.fileprovider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Błąd zapisu PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        pdf.close();
    }


    /**
     * Zwraca opis słowny średniej oceny.
     */
    private String getGradeDescription(double avg) {
        if (avg >= 5.5) return "Celujący";
        if (avg >= 4.5) return "Bardzo dobry";
        if (avg >= 3.5) return "Dobry";
        if (avg >= 2.5) return "Dostateczny";
        if (avg >= 1.5) return "Dopuszczający";
        return "Niedostateczny";
    }


    /**
     * Oblicza średnią arytmetyczną wszystkich ocen ucznia.
     */
    private String calculateOverallAverage(List<ClassResponse> subjects) {
        double sum = 0;
        int count = 0;
        for (ClassResponse s : subjects) {
            if (s.getAverage() != null) {
                sum += s.getAverage();
                count++;
            }
        }
        if (count == 0) return "--";
        return String.format(Locale.getDefault(), "%.2f", sum / count);
    }

    /**
     * Wznawia fragment i odświeża zdjęcie profilowe użytkownika.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (userId != -1) fetchLatestProfileImage(userId);
    }

    /**
     * Obsługuje rezultat wyboru zdjęcia z galerii, koduje je
     * do Base64 i wysyła na backend.
     */
    @Override
    public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == PICK_IMAGE_REQUEST && res == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try (InputStream is = requireContext().getContentResolver().openInputStream(uri)) {
                Bitmap bmp = BitmapFactory.decodeStream(is);
                profileImageView.setImageBitmap(bmp);

                String b64 = bitmapToBase64(bmp);
                propagateImageToHost(b64);
                sendImageToBackend(b64);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pobiera z backendu najnowsze zdjęcie profilowe użytkownika.
     */
    private void fetchLatestProfileImage(int id) {
        retrofit().create(UserApi.class)
                .getUserById(id)
                .enqueue(new Callback<Map<String, String>>() {
                    public void onResponse(Call<Map<String, String>> c, Response<Map<String, String>> r) {
                        if (!r.isSuccessful() || r.body() == null) return;

                        String b64 = r.body().get("image");
                        showBase64Image(b64);
                        propagateImageToHost(b64);
                    }

                    public void onFailure(Call<Map<String, String>> c, Throwable t) {
                        Log.e("FETCH", "Nie udało się pobrać zdjęcia: " + t.getMessage());
                    }
                });
    }


    /**
     * Tworzy instancję klienta Retrofit.
     */
    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Wysyła zakodowane zdjęcie profilowe na backend.
     */
    private void sendImageToBackend(String b64) {
        Map<String, String> body = new HashMap<>();
        body.put("image", b64);

        retrofit().create(UserApi.class)
                .uploadProfileImage(userId, body)
                .enqueue(new Callback<Void>() {
                    public void onResponse(Call<Void> c, Response<Void> r) {
                        if (!r.isSuccessful()) {
                            try {
                                Log.e("IMG_UPLOAD", "HTTP " + r.code() + " – " +
                                        (r.errorBody() != null ? r.errorBody().string() : "<no body>"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Toast.makeText(getContext(), "Błąd aktualizacji zdjęcia", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fetchLatestProfileImage(userId);
                    }

                    public void onFailure(Call<Void> c, Throwable t) {
                        Toast.makeText(getContext(), "Błąd połączenia", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Wyświetla zdjęcie profilowe zakodowane jako Base64 lub domyślne.
     */
    private void showBase64Image(String b64) {
        if (b64 != null && !b64.isEmpty())
            profileImageView.setImageBitmap(base64ToBitmap(b64));
        else
            profileImageView.setImageResource(R.drawable.profpic);
    }


    /**
     * Konwertuje bitmapę na ciąg Base64.
     */
    private static String bitmapToBase64(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
    }

    /**
     * Dekoduje ciąg Base64 na bitmapę.
     */
    private static Bitmap base64ToBitmap(String b64) {
        byte[] bytes = Base64.decode(b64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * Przekazuje nowe zdjęcie profilowe do aktywności głównej.
     */
    private void propagateImageToHost(String b64) {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).refreshProfileImage(b64);
        else if (getActivity() instanceof AdminMainActivity)
            ((AdminMainActivity) getActivity()).refreshProfileImage(b64);
    }

    /**
     * Ustawia język interfejsu aplikacji.
     */
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration cfg = new Configuration();
        cfg.setLocale(locale);
        getResources().updateConfiguration(cfg, getResources().getDisplayMetrics());
    }
}
