package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

/**
 * Fragment interfejsu administratora odpowiedzialny za zarządzanie klasami.
 * Umożliwia przejście do widoków:
 * - definiowania nowej klasy,
 * - przeglądania istniejących klas,
 * - przypisywania nauczycieli do klas.
 */
public class AdminClassesFragment extends Fragment {

    /**
     * Tworzy i inicjalizuje widok fragmentu. Obsługuje kliknięcia przycisków i
     * przełącza się do odpowiednich fragmentów administratora.
     *
     * @param inflater obiekt do "nadmuchiwania" widoków XML
     * @param container kontener dla widoku
     * @param savedInstanceState stan zapisany (jeśli istnieje)
     * @return zainicjalizowany widok główny fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_classes, container, false);

        ImageButton lookThroughClassesButton = rootView.findViewById(R.id.lookThroughClassesButton);
        ImageButton defineNewClassButton = rootView.findViewById(R.id.defineNewClassButton);
        defineNewClassButton.setOnClickListener(view -> {
            DefineNewClassFragment defineNewClassFragment = new DefineNewClassFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, defineNewClassFragment)
                    .addToBackStack(null)
                    .commit();
        });

        lookThroughClassesButton.setOnClickListener(view -> {
            LookThroughClassesFragment lookThroughClassesFragment = new LookThroughClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughClassesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        ImageButton assignTeacherToClassButton = rootView.findViewById(R.id.assignTeacherToClassButton);
        assignTeacherToClassButton.setOnClickListener(view -> {
            AssignTeacherToClassFragment assignTeacherToClassFragment = new AssignTeacherToClassFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, assignTeacherToClassFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}