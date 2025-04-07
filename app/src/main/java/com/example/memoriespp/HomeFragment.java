package com.example.memoriespp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView12 = rootView.findViewById(R.id.dateNow);

        Calendar calendar = Calendar.getInstance();

        String[] dniTygodnia = {
                "niedziela", "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota"
        };

        int dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        String message = "Dziś jest " + dniTygodnia[dayOfWeekIndex] + ", " + currentDate;
        textView12.setText(message);

        ImageButton gradesButton = rootView.findViewById(R.id.gradesButton);
        gradesButton.setOnClickListener(view -> {
            GradesFragment gradesFragment = new GradesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gradesFragment)
                    .addToBackStack(null) // Dodaje transakcję do stosu, by umożliwić powrót
                    .commit();
        });

        ImageButton classesButton = rootView.findViewById(R.id.classesButton);
        classesButton.setOnClickListener(view -> {
            ClassesFragment classesFragment = new ClassesFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, classesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        ImageButton teachersButton = rootView.findViewById(R.id.teachersButton);
        teachersButton.setOnClickListener(view -> {
            TeachersFragment teachersFragment = new TeachersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, teachersFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}
