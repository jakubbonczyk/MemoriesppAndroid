package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Fragment odpowiedzialny za widok zarządzania klasami/przedmiotami.
 * Obecnie pełni rolę kontenera lub placeholdera dla przyszłych funkcjonalności.
 */
public class ClassesFragment extends Fragment {

    /**
     * Tworzy i zwraca widok skojarzony z fragmentem.
     * Aktualnie ładuje layout fragmentu klas bez dodatkowej logiki.
     *
     * @param inflater obiekt LayoutInflater do tworzenia widoku
     * @param container kontener widoku nadrzędnego
     * @param savedInstanceState zapisany stan instancji (jeśli istnieje)
     * @return główny widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classes, container, false);


        return rootView;
    }

}