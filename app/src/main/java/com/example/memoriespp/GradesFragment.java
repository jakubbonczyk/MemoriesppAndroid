package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.Short4;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class GradesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        ImageButton seeGradeInfoButton = rootView.findViewById(R.id.classGradeButton);
        seeGradeInfoButton.setOnClickListener(view -> {
            GradeViewFragment gradeViewFragment = new GradeViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gradeViewFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView; // Poprawione - Zwracamy `rootView`, a nie ponowne `inflater.inflate`
    }

}