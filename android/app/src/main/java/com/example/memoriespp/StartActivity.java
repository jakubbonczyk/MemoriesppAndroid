package com.example.memoriespp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

/**
 * Ekran startowy aplikacji.
 * Odpowiada za:
 * - wyświetlenie przycisku przejścia do logowania
 * - opcjonalne wyświetlenie reklamy pełnoekranowej przed przejściem
 * - ustawienie języka aplikacji przy uruchomieniu
 */
public class StartActivity extends AppCompatActivity {

    public static boolean ENABLE_ADS = false;
    private InterstitialAd mInterstitialAd;
    private TextView button;

    /**
     * Ustawia lokalizację językową aplikacji na podstawie zapisanych preferencji.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }

    /**
     * Inicjalizuje widok ekranu startowego oraz reklamy pełnoekranowej.
     * Ustawia listener na przycisku uruchamiającym przejście do ekranu logowania
     * lub wyświetlającym reklamę, jeśli jest włączona.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = findViewById(R.id.button);

        button.setEnabled(!ENABLE_ADS);

        MobileAds.initialize(this, initializationStatus -> {});

        loadInterstitialAd();

        button.setOnClickListener(view -> {
            if (ENABLE_ADS && mInterstitialAd != null) {
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mInterstitialAd = null;
                        loadInterstitialAd();
                        goToLogin();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        mInterstitialAd = null;
                        loadInterstitialAd();
                        goToLogin();
                    }
                });

                mInterstitialAd.show(StartActivity.this);

            }
            else {
            goToLogin();
        }
        });
    }

    /**
     * Ładuje reklamę pełnoekranową i przypisuje ją do pola `mInterstitialAd`.
     * Po załadowaniu aktywuje przycisk przejścia dalej.
     */
    private void loadInterstitialAd() {
        if (ENABLE_ADS) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            if (button != null) {
                                button.setEnabled(true);
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            mInterstitialAd = null;
                            if (button != null) {
                                button.setEnabled(true);
                            }
                        }
                    });
        }
    }

    /**
     * Przechodzi do ekranu logowania (`LoginActivity`) i kończy bieżącą aktywność.
     */
    private void goToLogin() {
        Log.d("DEBUG", "Przechodzę do LoginActivity");
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
