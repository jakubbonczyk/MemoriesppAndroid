package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class GradeViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_grade_view, container, false);

        ImageButton seeSpecificGradeInfoButton = rootView.findViewById(R.id.classGradeInfoButton);

        seeSpecificGradeInfoButton.setOnClickListener(view -> {
            SpecificGradeViewFragment specifigGradeViewFragment = new SpecificGradeViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, specifigGradeViewFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
