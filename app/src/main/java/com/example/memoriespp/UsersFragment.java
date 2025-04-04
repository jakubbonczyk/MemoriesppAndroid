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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        ImageButton lookThroughUsersButton = rootView.findViewById(R.id.lookThroughUsersButton);

        lookThroughUsersButton.setOnClickListener(view -> {
            LookThroughUsersFragment lookThroughUsersFragment = new LookThroughUsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughUsersFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return rootView;
    }

}