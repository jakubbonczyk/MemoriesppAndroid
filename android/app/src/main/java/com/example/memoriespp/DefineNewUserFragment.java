package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class DefineNewUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_define_new_user, container, false);
        AppCompatButton addNewUserButton = rootView.findViewById(R.id.addNewUserButton);

        addNewUserButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Dodano użytkownika", Toast.LENGTH_SHORT).show();

            UsersFragment usersFragment = new UsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, usersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
