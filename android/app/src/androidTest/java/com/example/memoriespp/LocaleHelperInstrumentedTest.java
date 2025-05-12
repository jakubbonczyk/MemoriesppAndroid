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

        LocaleHelper.changeLanguage(context, "it");

        LocaleHelper.setLocale(context);

        assertEquals("it", Locale.getDefault().getLanguage());
    }
}
