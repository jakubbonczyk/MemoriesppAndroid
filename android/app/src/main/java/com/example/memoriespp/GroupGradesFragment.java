package com.example.memoriespp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Fragment odpowiedzialny za wyświetlanie listy uczniów przypisanych do danej grupy
 * wraz z ich ocenami. Umożliwia również przejście do widoku szczegółowego ocen danego ucznia.
 */
public class GroupGradesFragment extends Fragment {

    private LinearLayout gradeContainer;
    private GradeApi api;
    private int groupId;

    /**
     * Tworzy główny widok fragmentu i inicjuje pobieranie uczniów przypisanych do grupy.
     *
     * @param inflater inflater do tworzenia widoku
     * @param container kontener nadrzędny
     * @param savedInstanceState zapisany stan (jeśli istnieje)
     * @return główny widok fragmentu
     */
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

    /**
     * Pobiera z serwera listę uczniów przypisanych do danej grupy przy użyciu Retrofit.
     * W przypadku sukcesu przekazuje dane do metody `displayStudents(...)`.
     */
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

    /**
     * Wyświetla listę uczniów w layoucie, dodając przyciski do przeglądania ich ocen
     * oraz inicjalizując pobieranie ocen indywidualnych.
     *
     * @param list lista uczniów pobrana z serwera
     */
    private void displayStudents(List<StudentResponse> list) {
        gradeContainer.removeAllViews();

        for (StudentResponse student : list) {
            View item = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_student, gradeContainer, false);

            TextView tvName      = item.findViewById(R.id.studentName);
            ImageButton btnView  = item.findViewById(R.id.viewGradesButton);
            LinearLayout gradesLayout = item.findViewById(R.id.gradeListLayout);

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

            gradesLayout.removeAllViews();
            fetchGrades(student.getId(), gradesLayout);

            int pad = dpToPx(2);
            tvName.setPadding(
                    tvName.getPaddingLeft(),
                    tvName.getPaddingTop(),
                    tvName.getPaddingRight(),
                    pad
            );

            gradeContainer.addView(item);
        }
    }

    /**
     * Pomocnicza metoda konwertująca jednostki dp na px.
     *
     * @param dp wartość w dp
     * @return odpowiadająca wartość w px
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Pobiera oceny dla danego ucznia i wyświetla je w przekazanym layoucie.
     * Każda ocena jest prezentowana jako dynamicznie dodany TextView.
     *
     * @param studentId ID ucznia
     * @param targetLayout Layout, do którego dodawane są widoki ocen
     */
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

                targetLayout.removeAllViews();
                for (Grade g : grades) {
                    TextView gradeView = new TextView(getContext());
                    gradeView.setText(String.valueOf(g.getGrade()));
                    gradeView.setBackgroundResource(R.drawable.background_specific_grade);
                    gradeView.setTextSize(18f);
                    gradeView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    gradeView.setPadding(24, 20, 24, 20);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(12, 4, 0, 0);
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
