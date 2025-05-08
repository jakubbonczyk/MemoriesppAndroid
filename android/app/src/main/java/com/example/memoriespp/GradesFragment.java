package com.example.memoriespp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Locale;

import network.ClassResponse;
import network.GradeApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GradesFragment extends Fragment {

    private FrameLayout browseGradesLayout;
    private FrameLayout addGradeLayout;
    private LinearLayout gradesLayout;
    private String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);

        addGradeLayout       = rootView.findViewById(R.id.addGradeLayout);
        browseGradesLayout   = rootView.findViewById(R.id.browseGradesLayout);
        gradesLayout         = rootView.findViewById(R.id.gradesLayout);

        ImageButton addBtn   = rootView.findViewById(R.id.addGradeButton);
        ImageButton lookBtn  = rootView.findViewById(R.id.lookThroughGradesButton);

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        role       = prefs.getString("role", "");

        if ("S".equals(role)) {
            addGradeLayout.setVisibility(View.GONE);
            browseGradesLayout.setVisibility(View.GONE);
            gradesLayout.setVisibility(View.VISIBLE);

            if (userId != -1) {
                fetchSubjectsForStudent(userId);
            } else {
                Toast.makeText(getContext(),
                        "Nie można pobrać ID użytkownika",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            addGradeLayout.setVisibility(View.VISIBLE);
            browseGradesLayout.setVisibility(View.VISIBLE);
            gradesLayout.setVisibility(View.GONE);
        }

        addBtn.setOnClickListener(v -> {
            SelectGroupGradesFragment frag = new SelectGroupGradesFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag)
                    .addToBackStack(null)
                    .commit();
        });

        lookBtn.setOnClickListener(v -> {
            LookThroughGradesFragment frag = new LookThroughGradesFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

    private void fetchSubjectsForStudent(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi api = retrofit.create(GradeApi.class);
        api.getSubjectsForStudent(userId)
                .enqueue(new Callback<List<ClassResponse>>() {
                    @Override
                    public void onResponse(Call<List<ClassResponse>> call,
                                           Response<List<ClassResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            displaySubjects(response.body());
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania przedmiotów",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClassResponse>> call, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displaySubjects(List<ClassResponse> subjects) {
        gradesLayout.removeAllViews();

        for (ClassResponse subject : subjects) {
            View subjectView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_subject, gradesLayout, false);

            TextView subjectName    = subjectView.findViewById(R.id.subjectName);
            TextView subjectAverage = subjectView.findViewById(R.id.subjectAverage);
            ImageButton subjectButton = subjectView.findViewById(R.id.subjectButton);

            subjectName.setText(subject.getClassName());
            Double avg = subject.getAverage();
            if (avg != null) {
                subjectAverage.setText(
                        String.format(Locale.getDefault(), "%.2f", avg)
                );
            } else {
                subjectAverage.setText("--");
            }

            subjectButton.setOnClickListener(view -> {
                GradeViewFragment frag = new GradeViewFragment();
                Bundle args = new Bundle();
                args.putInt("subjectId", subject.getId());
                args.putString("subjectName", subject.getClassName());
                frag.setArguments(args);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, frag)
                        .addToBackStack(null)
                        .commit();
            });

            gradesLayout.addView(subjectView);
        }
    }
}
