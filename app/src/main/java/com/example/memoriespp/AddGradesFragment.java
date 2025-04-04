package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class AddGradesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_grades, container, false);

        AppCompatButton addGradeButton = rootView.findViewById(R.id.addGradeButton);
        addGradeButton.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Pomyślnie dodano ocenę", Toast.LENGTH_LONG).show();

            GradesFragment gradeFragment = new GradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gradeFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}