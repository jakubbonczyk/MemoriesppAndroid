package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Fragment wyświetlający listę nauczycieli dla administratora.
 * Zawiera widok z nauczycielami oraz interfejs do zarządzania nimi.
 */
public class TeachersFragment extends Fragment {

    /**
     * Tworzy i zwraca widok fragmentu nauczycieli.
     * Inicjalizuje layout fragmentu bez dodatkowej logiki.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teachers, container, false);


        return rootView;
    }

}