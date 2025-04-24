package com.example.memoriespp;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class LocaleHelperInstrumentedTest {

    @Test
    public void testSetLocaleSetsCorrectLanguage() {
        Context context = ApplicationProvider.getApplicationContext();

        // Najpierw ustawiamy język (czyli zapisujemy do prefs)
        LocaleHelper.changeLanguage(context, "it");

        // Potem wywołujemy metodę, która powinna go odczytać i ustawić
        LocaleHelper.setLocale(context);

        // Sprawdzamy czy Locale.getDefault() ma teraz włoski
        assertEquals("it", Locale.getDefault().getLanguage());
    }
}
