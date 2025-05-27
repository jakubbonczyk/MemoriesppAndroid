package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

/**
 * Fragment odpowiedzialny za zarządzanie użytkownikami w systemie.
 * Umożliwia administratorowi przejście do ekranu przeglądania użytkowników,
 * dodawania nowych oraz przypisywania nauczycieli do grup.
 */
public class UsersFragment extends Fragment {

    /**
     * Tworzy widok fragmentu zarządzania użytkownikami.
     * Inicjalizuje przyciski do przeglądania, definiowania i przypisywania nauczycieli,
     * oraz ustawia akcje nawigacyjne do odpowiednich fragmentów.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        ImageButton lookThroughUsersButton = rootView.findViewById(R.id.lookThroughUsersButton);
        ImageButton defineNewUserButton = rootView.findViewById(R.id.defineNewUserButton);
        ImageButton assignTeachersToGroupsButton = rootView.findViewById(R.id.assignTeachersToGroupsButton);

        lookThroughUsersButton.setOnClickListener(view -> {
            LookThroughUsersFragment lookThroughUsersFragment = new LookThroughUsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughUsersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        defineNewUserButton.setOnClickListener(view -> {
            DefineNewUserFragment defineNewUserFragment = new DefineNewUserFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, defineNewUserFragment)
                    .addToBackStack(null)
                    .commit();
        });

        assignTeachersToGroupsButton.setOnClickListener(view -> {
            AssignTeacherToGroupFragment assignTeacherToGroupFragment = new AssignTeacherToGroupFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, assignTeacherToGroupFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}