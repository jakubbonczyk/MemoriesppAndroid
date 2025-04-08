package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class EditUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_user, container, false);

        AppCompatButton resetPasswordButton = rootView.findViewById(R.id.resetPasswordButton);
        AppCompatButton saveUserButton = rootView.findViewById(R.id.saveUserButton);

        resetPasswordButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Zresetowano hasło", Toast.LENGTH_SHORT).show();

            UsersFragment usersFragment = new UsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, usersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        saveUserButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Zapisano zmiany", Toast.LENGTH_SHORT).show();

            UsersFragment usersFragment = new UsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, usersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
