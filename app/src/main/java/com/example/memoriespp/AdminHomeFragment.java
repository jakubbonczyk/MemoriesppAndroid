package com.example.memoriespp;

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

        ImageButton gradesButton = rootView.findViewById(R.id.gradesButton);
        gradesButton.setOnClickListener(view -> {
            GradesFragment gradesFragment = new GradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gradesFragment)
                    .addToBackStack(null) // Dodaje transakcję do stosu, by umożliwić powrót
                    .commit();
        });

        return rootView;
    }
}
