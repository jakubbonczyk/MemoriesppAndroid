package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

/**
 * Fragment służący do edycji informacji o grupie.
 * Po kliknięciu przycisku zapisu użytkownik zostaje przekierowany
 * z powrotem do ekranu zarządzania grupami.
 */
public class EditGroupFragment extends Fragment {

    /**
     * Tworzy i zwraca widok fragmentu, ustawiając akcję dla przycisku zapisu.
     * Aktualnie symuluje zapis grupy poprzez wyświetlenie komunikatu i przejście
     * do ekranu AdminGroupsFragment.
     *
     * @param inflater obiekt LayoutInflater służący do tworzenia widoku z pliku XML
     * @param container widok nadrzędny, do którego zostanie dołączony ten fragment
     * @param savedInstanceState zapisany stan fragmentu (jeśli istnieje)
     * @return widok dla tego fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_group, container, false);
        AppCompatButton saveGroupButton = rootView.findViewById(R.id.saveGroupButton);

        saveGroupButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Zapisano grupę", Toast.LENGTH_SHORT).show();

            AdminGroupsFragment adminGroupsFragment = new AdminGroupsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminGroupsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
