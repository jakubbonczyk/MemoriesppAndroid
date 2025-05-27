package com.example.memoriespp;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;

/**
 * Testy jednostkowe dla klasy {@link LocaleHelper}.
 * Testują metodę {@link LocaleHelper#changeLanguage(Context, String)}, która zmienia język aplikacji
 * i zapisuje go w preferencjach użytkownika.
 */
public class LocaleHelperTest {

    /**
     * Testuje, czy metoda {@link LocaleHelper#changeLanguage(Context, String)} poprawnie zapisuje wybrany język
     * w preferencjach aplikacji.
     */
    @Test
    public void testChangeLanguageStoresLanguageInPrefs() {
        Context mockContext = mock(Context.class);
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        SharedPreferences mockPrefs = mock(SharedPreferences.class);

        when(mockContext.getSharedPreferences("settings", Context.MODE_PRIVATE)).thenReturn(mockPrefs);
        when(mockPrefs.edit()).thenReturn(mockEditor);
        when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);

        LocaleHelper.changeLanguage(mockContext, "es");

        verify(mockEditor).putString("language", "es");
        verify(mockEditor).apply();
    }
}
