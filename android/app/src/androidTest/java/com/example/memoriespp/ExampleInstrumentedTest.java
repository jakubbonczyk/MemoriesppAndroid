package com.example.memoriespp;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Przykładowy test instrumentalny uruchamiany na urządzeniu Android.
 * Sprawdza poprawność kontekstu aplikacji – czy pakiet aplikacji jest zgodny z oczekiwanym.
 *
 * @see <a href="http://d.android.com/tools/testing">Dokumentacja testowania Android</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /**
     * Testuje, czy kontekst aplikacji ma poprawną nazwę pakietu.
     */
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.memoriespp", appContext.getPackageName());
    }
}