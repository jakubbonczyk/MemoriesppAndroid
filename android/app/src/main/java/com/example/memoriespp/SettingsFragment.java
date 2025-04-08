package com.example.memoriespp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button logoutButton = rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Poprawnie wylogowano", Toast.LENGTH_SHORT).show();
        });

        // Obsługa zmiany języka
        Button changeLanguageButton = rootView.findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(view -> {
            String currentLanguage = Locale.getDefault().getLanguage();
            String newLanguage = currentLanguage.equals("pl") ? "en" : "pl";

            LocaleHelper.changeLanguage(requireContext(), newLanguage);
            requireActivity().recreate();
        });




        return rootView;
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getResources().updateConfiguration(configuration, displayMetrics);
    }
}
