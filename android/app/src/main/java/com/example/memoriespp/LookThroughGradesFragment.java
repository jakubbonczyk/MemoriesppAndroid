package com.example.memoriespp;

import static android.content.Context.MODE_PRIVATE;

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
import network.TeacherGroupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment pozwalający nauczycielowi przeglądać oceny uczniów
 * przypisanych do grup, które prowadzi.
 */
public class LookThroughGradesFragment extends Fragment {
    private LinearLayout classesLayout;
    private GradeApi api;
    private int teacherId;

    /**
     * Tworzy widok fragmentu i inicjalizuje zapytanie o grupy prowadzone
     * przez aktualnie zalogowanego nauczyciela.
     *
     * @param inf obiekt LayoutInflater do tworzenia widoku
     * @param c kontener widoku-rodzica
     * @param b zapisany stan (jeśli istnieje)
     * @return korzeń widoku fragmentu
     */
    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle b) {
        View root = inf.inflate(R.layout.fragment_look_through_grades, c, false);
        classesLayout = root.findViewById(R.id.classesLayout);

        teacherId = requireActivity()
                .getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

        api = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GradeApi.class);

        api.getGroupsForTeacher(teacherId).enqueue(new Callback<List<TeacherGroupResponse>>() {
            @Override
            public void onResponse(Call<List<TeacherGroupResponse>> c, Response<List<TeacherGroupResponse>> r) {
                if (r.isSuccessful() && r.body()!=null) displayGroups(r.body());
            }
            @Override public void onFailure(Call<List<TeacherGroupResponse>> c, Throwable t) {
                Toast.makeText(getContext(), "Błąd: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    /**
     * Wyświetla listę grup przypisanych do nauczyciela.
     * Każda pozycja zawiera przycisk do przejścia do ocen uczniów w tej grupie.
     *
     * @param list lista grup nauczyciela
     */
    private void displayGroups(List<TeacherGroupResponse> list) {
        classesLayout.removeAllViews();
        for (TeacherGroupResponse g : list) {
            View v = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_teacher_class2, classesLayout, false);
            TextView name = v.findViewById(R.id.className);
            ImageButton btn = v.findViewById(R.id.classButton);
            name.setText(g.getGroupName());
            btn.setOnClickListener(x -> {
                GroupGradesFragment frag = new GroupGradesFragment();
                Bundle args = new Bundle();
                args.putInt("groupId", g.getId());
                frag.setArguments(args);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, frag)
                        .addToBackStack(null)
                        .commit();
            });

            classesLayout.addView(v);
        }
    }
}
