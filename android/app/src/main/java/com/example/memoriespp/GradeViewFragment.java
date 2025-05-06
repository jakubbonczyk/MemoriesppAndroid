package com.example.memoriespp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import network.GradeApi;
import network.GradeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GradeViewFragment extends Fragment {

    private LinearLayout gradesLayout;
    private int subjectId;
    private String subjectName;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_grade_view, container, false);
        gradesLayout = rootView.findViewById(R.id.gradesLayout); // <- Upewnij się, że to ID jest w XML!

        // Odczyt argumentów
        Bundle args = getArguments();
        if (args != null) {
            subjectId = args.getInt("subjectId");
            subjectName = args.getString("subjectName");
        }

        // Odczyt userId z SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1 && subjectId != -1) {
            fetchGrades(userId, subjectId);
        } else {
            Toast.makeText(getContext(), "Błąd: brak danych ucznia lub przedmiotu", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void fetchGrades(int studentId, int subjectId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi gradeApi = retrofit.create(GradeApi.class);
        Call<List<GradeResponse>> call = gradeApi.getGradesForSubject(studentId, subjectId);

        call.enqueue(new Callback<List<GradeResponse>>() {
            @Override
            public void onResponse(Call<List<GradeResponse>> call, Response<List<GradeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayGrades(response.body());
                } else {
                    Toast.makeText(getContext(), "Błąd pobierania ocen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GradeResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayGrades(List<GradeResponse> grades) {
        gradesLayout.removeAllViews();

        for (GradeResponse grade : grades) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_grade, gradesLayout, false);

            TextView category = view.findViewById(R.id.defineNewUser);
            TextView date = view.findViewById(R.id.textView10);
            TextView gradeText = view.findViewById(R.id.gradesAverage);
            ImageButton button = view.findViewById(R.id.classGradeInfoButton);

            category.setText(grade.getType());
            date.setText(grade.getFormattedDate());
            gradeText.setText(String.valueOf(grade.getGrade()));

            button.setOnClickListener(v -> {
                SpecificGradeViewFragment fragment = new SpecificGradeViewFragment();
                Bundle args = new Bundle();
                args.putInt("grade", grade.getGrade());
                args.putString("description", grade.getDescription());
                args.putString("teacher", grade.getTeacherName());
                fragment.setArguments(args);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            });

            gradesLayout.addView(view);
        }
    }
}
