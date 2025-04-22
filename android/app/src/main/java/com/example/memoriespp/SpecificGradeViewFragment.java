package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SpecificGradeViewFragment extends Fragment {

    private TextView gradeText;
    private TextView categoryText;
    private TextView teacherText;
    private TextView dateText;
    private TextView descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specific_grade_view, container, false);

        gradeText = rootView.findViewById(R.id.grade);
        categoryText = rootView.findViewById(R.id.gradeCategory);
        teacherText = rootView.findViewById(R.id.gradeTeacher);
        dateText = rootView.findViewById(R.id.gradeDate);
        descriptionText = rootView.findViewById(R.id.gradeDescription);

        Bundle args = getArguments();
        if (args != null) {
            int gradeValue = args.getInt("grade", 0);
            String description = args.getString("description", "Brak opisu");
            String teacher = args.getString("teacher", "Brak nauczyciela");

            gradeText.setText(String.valueOf(gradeValue));
            categoryText.setText("Ocena"); // Możesz tu dodać kategorię jeśli masz ją w przyszłości
            teacherText.setText("Nauczyciel: " + teacher);
            dateText.setText("Brak daty"); // jeśli w przyszłości dodasz datę, można ją tu wstawić
            descriptionText.setText(description);
        }

        return rootView;
    }
}
