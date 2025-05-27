package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

/**
 * Fragment interfejsu administratora odpowiedzialny za zarządzanie grupami.
 * Umożliwia przejście do widoków:
 * - przeglądania istniejących grup,
 * - definiowania nowej grupy,
 * - zarządzania planem zajęć.
 */
public class AdminGroupsFragment extends Fragment {

    /**
     * Tworzy i inicjalizuje widok fragmentu. Ustawia akcje kliknięcia dla przycisków
     * nawigujących do odpowiednich fragmentów administracyjnych.
     *
     * @param inflater obiekt używany do „nadmuchiwania” layoutu
     * @param container kontener rodzica (ViewGroup)
     * @param savedInstanceState poprzedni stan (jeśli istnieje)
     * @return zainicjalizowany widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_groups, container, false);

        ImageButton defineNewGroupButton = rootView.findViewById(R.id.defineNewGroupButton);
        ImageButton lookThroughGroupsButton = rootView.findViewById(R.id.lookThroughGroupsButton);
        ImageButton manageScheduleButton   = rootView.findViewById(R.id.manageScheduleButton);

        lookThroughGroupsButton.setOnClickListener(view -> {
            LookThroughGroupsFragment lookThroughGroupsFragment = new LookThroughGroupsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughGroupsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        defineNewGroupButton.setOnClickListener(view -> {
            DefineNewGroupFragment defineNewGroupFragment = new DefineNewGroupFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, defineNewGroupFragment)
                    .addToBackStack(null)
                    .commit();
        });

        manageScheduleButton.setOnClickListener(v -> {
            ScheduleFragment fragment = new ScheduleFragment();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}