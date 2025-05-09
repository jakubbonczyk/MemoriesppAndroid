package com.example.memoriespp;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

import network.Grade;
import network.GradeApi;
import network.StudentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupGradesFragment extends Fragment {

    private LinearLayout gradeContainer;
    private GradeApi api;
    private int groupId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_grades, container, false);
        gradeContainer = root.findViewById(R.id.userGradeSection);

        groupId = getArguments() != null
                ? getArguments().getInt("groupId", -1)
                : -1;

        if (groupId == -1) {
            Toast.makeText(getContext(), "Brak identyfikatora grupy", Toast.LENGTH_SHORT).show();
            return root;
        }

        api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GradeApi.class);

        fetchStudentsForGroup();

        return root;
    }

    private void fetchStudentsForGroup() {
        api.getStudentsForGroup(groupId).enqueue(new Callback<List<StudentResponse>>() {
            @Override
            public void onResponse(Call<List<StudentResponse>> call, Response<List<StudentResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getContext(), "Błąd: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                displayStudents(response.body());
            }

            @Override
            public void onFailure(Call<List<StudentResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayStudents(List<StudentResponse> list) {
        gradeContainer.removeAllViews();

        for (StudentResponse student : list) {
            // 1) Inflateujemy gotowy item_student.xml
            View item = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_student, gradeContainer, false);

            TextView tvName      = item.findViewById(R.id.studentName);
            ImageButton btnView  = item.findViewById(R.id.viewGradesButton);
            LinearLayout gradesLayout = item.findViewById(R.id.gradeListLayout);

            // 2) Ustawiamy imię i kliknięcie
            String fullName = student.getName() + " " + student.getSurname();
            tvName.setText(fullName);
            btnView.setOnClickListener(v -> {
                GradeViewFragment frag = new GradeViewFragment();
                Bundle args = new Bundle();
                args.putInt("studentId", student.getId());
                frag.setArguments(args);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, frag)
                        .addToBackStack(null)
                        .commit();
            });

            // 3) Czyścimy i ładujemy oceny
            gradesLayout.removeAllViews();
            fetchGrades(student.getId(), gradesLayout);

            // 4) (opcjonalnie) lekko pogrubiamy tekst lub dodajemy padding
            int pad = dpToPx(2);
            tvName.setPadding(
                    tvName.getPaddingLeft(),
                    tvName.getPaddingTop(),
                    tvName.getPaddingRight(),
                    pad
            );

            // 5) Dodajemy wiersz do głównego kontenera
            gradeContainer.addView(item);
        }
    }

    // Helper: dp -> px
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }





    private void fetchGrades(int studentId, LinearLayout targetLayout) {
        api.getGradesForStudent(studentId).enqueue(new Callback<List<Grade>>() {
            @Override
            public void onResponse(Call<List<Grade>> call, Response<List<Grade>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("fetchGrades", "Brak ocen dla studentId=" + studentId);
                    return;
                }

                List<Grade> grades = response.body();
                Log.d("fetchGrades", "Pobrano " + grades.size() + " ocen dla studentId=" + studentId);

                targetLayout.removeAllViews(); // ⬅️ najważniejsze

                for (Grade g : grades) {
                    TextView gradeView = new TextView(getContext());
                    gradeView.setText(String.valueOf(g.getGrade()));
                    gradeView.setBackgroundResource(R.drawable.background_specific_grade);
                    gradeView.setTextSize(18f);
                    gradeView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    gradeView.setPadding(24, 20, 24, 20); // cieńsze, kwadratowe

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(12, 4, 0, 0); // mniejszy top margin
                    gradeView.setLayoutParams(params);

                    targetLayout.addView(gradeView);
                }
            }

            @Override
            public void onFailure(Call<List<Grade>> call, Throwable t) {
                Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
