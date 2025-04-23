package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.Short4;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GradesFragment extends Fragment {

    FrameLayout browseGradesLayout;
    FrameLayout addGradeLayout;
    LinearLayout gradesLayout;
    String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        ImageButton seeGradeInfoButton = rootView.findViewById(R.id.classGradeButton);
        ImageButton addGradeButton = rootView.findViewById(R.id.addGradeButton);
        ImageButton lookThroughGradesButton = rootView.findViewById(R.id.lookThroughGradesButton);

        seeGradeInfoButton.setOnClickListener(view -> {
            GradeViewFragment gradeViewFragment = new GradeViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gradeViewFragment)
                    .addToBackStack(null)
                    .commit();
        });


        addGradeButton.setOnClickListener(view -> {
            SelectGroupGradesFragment selectGroupGradesFragment = new SelectGroupGradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, selectGroupGradesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        lookThroughGradesButton.setOnClickListener(view -> {
            LookThroughGradesFragment lookThroughGradesFragment = new LookThroughGradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, lookThroughGradesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        addGradeLayout = rootView.findViewById(R.id.addGradeLayout);
        browseGradesLayout = rootView.findViewById(R.id.browseGradesLayout);
        gradesLayout = rootView.findViewById(R.id.gradesLayout);

        if(getArguments() != null) {
            role = getArguments().getString("role");
        }

        if("S".equals(role)) {
            addGradeLayout.setVisibility(View.GONE);
            browseGradesLayout.setVisibility(View.GONE);
            gradesLayout.setVisibility(View.VISIBLE);
        } else {
            addGradeLayout.setVisibility(View.VISIBLE);
            browseGradesLayout.setVisibility(View.VISIBLE);
            gradesLayout.setVisibility(View.GONE);
        }

        return rootView;
    }
}