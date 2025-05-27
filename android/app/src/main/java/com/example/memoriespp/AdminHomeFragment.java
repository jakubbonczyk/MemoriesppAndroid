package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

/**
 * Ekran główny administratora, wyświetlany po zalogowaniu.
 * Umożliwia szybki dostęp do trzech sekcji: użytkowników, grup i klas.
 */
public class AdminHomeFragment extends Fragment {

    /**
     * Tworzy i inicjalizuje widok fragmentu. Obsługuje kliknięcia w przyciski
     * nawigujące do fragmentów zarządzania użytkownikami, grupami i klasami.
     *
     * @param inflater obiekt służący do tworzenia widoku z XML-a
     * @param container widok rodzica (np. FrameLayout)
     * @param savedInstanceState stan zapisany (jeśli istnieje)
     * @return główny widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_home, container, false);

        ImageButton manageUsersButton = rootView.findViewById(R.id.manageUsersButton);
        ImageButton manageGroupsButton = rootView.findViewById(R.id.manageGroupsButton);
        ImageButton manageClassesButton = rootView.findViewById(R.id.manageClassesButton);

        manageGroupsButton.setOnClickListener(view -> {
            AdminGroupsFragment adminGroupsFragment = new AdminGroupsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminGroupsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        manageUsersButton.setOnClickListener(view -> {
            UsersFragment usersFragment = new UsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, usersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        manageClassesButton.setOnClickListener(view -> {
            AdminClassesFragment adminClassesFragment = new AdminClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminClassesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
