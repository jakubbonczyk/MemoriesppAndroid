package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

/**
 * Fragment służący do edycji informacji o przedmiocie.
 * Po zapisaniu zmian użytkownik jest przenoszony z powrotem do ekranu zarządzania przedmiotami.
 */
public class EditClassFragment extends Fragment {

    /**
     * Inicjalizuje widok fragmentu oraz ustawia akcję dla przycisku zapisu.
     * Obecnie wyświetla komunikat Toast i przekierowuje użytkownika do ekranu AdminClassesFragment.
     *
     * @param inflater obiekt służący do "nadmuchania" layoutu fragmentu
     * @param container kontener, do którego zostanie dołączony widok
     * @param savedInstanceState zapisany stan fragmentu (jeśli istnieje)
     * @return zainicjalizowany widok fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_class, container, false);
        AppCompatButton saveClassButton = rootView.findViewById(R.id.saveClassButton);

        saveClassButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Dodano przedmiot", Toast.LENGTH_SHORT).show();

            AdminClassesFragment adminClassesFragment = new AdminClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, adminClassesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
