package com.example.memoriespp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Klasa pomocnicza do zarządzania lokalizacją aplikacji (językiem interfejsu).
 * Pozwala na dynamiczną zmianę języka oraz jego zapamiętywanie w preferencjach.
 */
public class LocaleHelper {

    private static final String PREF_NAME = "settings";
    private static final String KEY_LANGUAGE = "language";

    /**
     * Ustawia język aplikacji na podstawie zapisanych preferencji użytkownika.
     *
     * @param context kontekst aplikacji lub aktywności
     * @return zmodyfikowany kontekst z nowymi ustawieniami językowymi
     */
    public static Context setLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String language = prefs.getString(KEY_LANGUAGE, "pl");
        return updateResources(context, language);
    }

    /**
     * Zmienia język aplikacji i zapisuje nowy kod języka w preferencjach.
     *
     * @param context      kontekst aplikacji lub aktywności
     * @param languageCode kod języka (np. "pl", "en", "de")
     */
    public static void changeLanguage(Context context, String languageCode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_LANGUAGE, languageCode);
        editor.apply();
    }

    /**
     * Aktualizuje zasoby aplikacji dla podanego języka.
     * Metoda stosowana wewnętrznie po zmianie języka.
     *
     * @param context  kontekst aplikacji lub aktywności
     * @param language kod języka (np. "pl", "en", "de")
     * @return zmodyfikowany kontekst z nową lokalizacją
     */
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}
