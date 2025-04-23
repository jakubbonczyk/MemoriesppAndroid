package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class UsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        ImageButton lookThroughUsersButton = rootView.findViewById(R.id.lookThroughUsersButton);
        ImageButton defineNewUserButton = rootView.findViewById(R.id.defineNewUserButton);

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

        return rootView;
    }

}