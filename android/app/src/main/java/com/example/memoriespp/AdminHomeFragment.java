package com.example.memoriespp;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminHomeFragment extends Fragment {

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
