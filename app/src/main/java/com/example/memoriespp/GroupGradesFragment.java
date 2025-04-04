package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class GroupGradesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_grades, container, false);

        Button grade1 = rootView.findViewById(R.id.grade1);
        grade1.setOnClickListener(view -> {
            SpecificGradeViewFragment specificGradeViewFragment = new SpecificGradeViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, specificGradeViewFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}