package com.example.memoriespp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;

public class LocaleHelperTest {

    @Test
    public void testChangeLanguageStoresLanguageInPrefs() {
        // Przygotowanie mocków
        Context mockContext = mock(Context.class);
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        SharedPreferences mockPrefs = mock(SharedPreferences.class);

        // Ustawienie zachowań mocków
        when(mockContext.getSharedPreferences("settings", Context.MODE_PRIVATE)).thenReturn(mockPrefs);
        when(mockPrefs.edit()).thenReturn(mockEditor);
        when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);

        // Wywołanie testowanej metody
        LocaleHelper.changeLanguage(mockContext, "es");

        // Sprawdzenie czy zapisano do prefs odpowiedni język
        verify(mockEditor).putString("language", "es");
        verify(mockEditor).apply();
    }
}
