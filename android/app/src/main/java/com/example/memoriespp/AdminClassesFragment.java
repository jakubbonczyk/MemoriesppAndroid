package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class AdminClassesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_classes, container, false);

        ImageButton lookThroughClassesButton = rootView.findViewById(R.id.lookThroughClassesButton);
        ImageButton defineNewClassButton = rootView.findViewById(R.id.defineNewClassButton);
        defineNewClassButton.setOnClickListener(view -> {
            DefineNewClassFragment defineNewClassFragment = new DefineNewClassFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, defineNewClassFragment)
                    .addToBackStack(null)
                    .commit();
        });

        lookThroughClassesButton.setOnClickListener(view -> {
            LookThroughClassesFragment lookThroughClassesFragment = new LookThroughClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughClassesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        ImageButton assignTeacherToClassButton = rootView.findViewById(R.id.assignTeacherToClassButton);
        assignTeacherToClassButton.setOnClickListener(view -> {
            AssignTeacherToClassFragment assignTeacherToClassFragment = new AssignTeacherToClassFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, assignTeacherToClassFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}