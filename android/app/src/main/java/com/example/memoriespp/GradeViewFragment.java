package com.example.memoriespp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import network.GradeApi;
import network.GradeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment odpowiedzialny za wyświetlanie listy ocen ucznia dla konkretnego przedmiotu.
 * Pobiera dane z API na podstawie ID ucznia (z SharedPreferences) i ID przedmiotu (z argumentów Bundle).
 * Pozwala użytkownikowi przejść do widoku szczegółowego pojedynczej oceny.
 */
public class GradeViewFragment extends Fragment {

    private LinearLayout gradesLayout;
    private int subjectId;
    private int userId;

    /**
     * Tworzy główny widok fragmentu i inicjalizuje referencję do kontenera ocen.
     *
     * @param inflater inflater do tworzenia widoku
     * @param container kontener nadrzędny
     * @param savedInstanceState zapisany stan (jeśli istnieje)
     * @return główny widok fragmentu
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_class_grade_view, container, false);
        gradesLayout = root.findViewById(R.id.gradesLayout);
        return root;
    }

    /**
     * Wywoływane po utworzeniu widoku. Pobiera identyfikatory ucznia i przedmiotu
     * oraz inicjuje pobieranie ocen.
     *
     * @param view widok stworzony przez onCreateView
     * @param savedInstanceState zapisany stan (jeśli istnieje)
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            subjectId = args.getInt("subjectId", -1);
        }

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        if (userId < 0 || subjectId < 0) {
            Toast.makeText(getContext(),
                    "Brak danych ucznia lub przedmiotu", Toast.LENGTH_SHORT).show();
        } else {
            fetchGrades(userId, subjectId);
        }
    }

    /**
     * Wysyła zapytanie do API w celu pobrania ocen danego ucznia z konkretnego przedmiotu.
     *
     * @param studentId ID ucznia
     * @param subjectId ID przedmiotu
     */
    private void fetchGrades(int studentId, int subjectId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi api = retrofit.create(GradeApi.class);
        api.getGradesForSubject(studentId, subjectId)
                .enqueue(new Callback<List<GradeResponse>>() {
                    @Override
                    public void onResponse(Call<List<GradeResponse>> call,
                                           Response<List<GradeResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            displayGrades(response.body());
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania ocen: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GradeResponse>> call, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Wyświetla listę ocen w kontenerze widoku. Dla każdej oceny tworzony jest osobny widok z przyciskiem do szczegółów.
     *
     * @param grades lista ocen zwrócona z API
     */
    private void displayGrades(List<GradeResponse> grades) {
        gradesLayout.removeAllViews();

        for (GradeResponse grade : grades) {
            View item = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_grade, gradesLayout, false);

            TextView typeTv    = item.findViewById(R.id.defineNewUser);
            TextView dateTv    = item.findViewById(R.id.textView10);
            TextView gradeTv   = item.findViewById(R.id.gradesAverage);
            ImageButton infoBtn= item.findViewById(R.id.classGradeInfoButton);

            typeTv.setText(grade.getType());
            dateTv.setText(grade.getIssueDate());
            gradeTv.setText(String.valueOf(grade.getGrade()));

            Log.d("GradeView", "ID=" + grade.getId()
                    + " type=" + grade.getType()
                    + " date=" + grade.getIssueDate());

            infoBtn.setOnClickListener(v -> {
                SpecificGradeViewFragment frag = new SpecificGradeViewFragment();
                Bundle b = new Bundle();
                b.putInt("gradeId", grade.getId());
                frag.setArguments(b);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, frag)
                        .addToBackStack(null)
                        .commit();
            });

            gradesLayout.addView(item);
        }
    }
}
