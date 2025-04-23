package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

import network.Grade;
import network.GradeApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupGradesFragment extends Fragment {

    private LinearLayout gradeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_grades, container, false);

        gradeContainer = rootView.findViewById(R.id.userGradeSection);

        int studentId = getActivity().getIntent().getIntExtra("userId", -1);
        if (studentId != -1) {
            fetchGradesForStudent(studentId);
        } else {
            Toast.makeText(getContext(), "Nie znaleziono ID ucznia", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void fetchGradesForStudent(int studentId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi gradeApi = retrofit.create(GradeApi.class);

        gradeApi.getGradesForStudent(studentId).enqueue(new Callback<List<Grade>>() {
            @Override
            public void onResponse(Call<List<Grade>> call, Response<List<Grade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Grade> gradeList = response.body();
                    populateGradeButtons(gradeList);
                } else {
                    Toast.makeText(getContext(), "Nie udało się pobrać ocen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Grade>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateGradeButtons(List<Grade> gradeList) {
        LinearLayout horizontalLayout = new LinearLayout(getContext());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (Grade grade : gradeList) {
            Button gradeButton = new Button(getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (60 * getResources().getDisplayMetrics().density),
                    (int) (60 * getResources().getDisplayMetrics().density)
            );
            params.setMargins(0, 0, (int) (10 * getResources().getDisplayMetrics().density), 0);

            gradeButton.setLayoutParams(params);
            gradeButton.setText(String.valueOf(grade.getGrade()));
            gradeButton.setTextColor(ContextCompat.getColor(getContext(), R.color.light_blue));
            gradeButton.setTextSize(24f);
            gradeButton.setBackgroundResource(R.drawable.background_specific_grade);

            gradeButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("grade", grade.getGrade());
                bundle.putString("description", grade.getDescription());
                bundle.putString("teacher", grade.getTeacherName());

                SpecificGradeViewFragment fragment = new SpecificGradeViewFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            });

            horizontalLayout.addView(gradeButton);
        }

        gradeContainer.removeAllViews();
        gradeContainer.addView(horizontalLayout);
    }
}
