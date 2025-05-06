package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import network.GradeApi;
import network.GradeDetailDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificGradeViewFragment extends Fragment {

    private int gradeId;
    private TextView gradeTv,
            categoryTv,
            teacherTv,
            typeTv,
            dateTv,
            descriptionTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_grade_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gradeTv       = view.findViewById(R.id.grade);
        categoryTv    = view.findViewById(R.id.gradeCategory);
        teacherTv     = view.findViewById(R.id.gradeTeacher);
        typeTv        = view.findViewById(R.id.gradeType);
        dateTv        = view.findViewById(R.id.gradeDate);
        descriptionTv = view.findViewById(R.id.gradeDescription);

        if (getArguments() != null) {
            gradeId = getArguments().getInt("gradeId", -1);
        }
        if (gradeId < 0) {
            Toast.makeText(getContext(),
                    "Brak ID oceny", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchGradeDetails(gradeId);
    }

    private void fetchGradeDetails(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GradeApi api = retrofit.create(GradeApi.class);
        api.getGradeDetails(id)
                .enqueue(new Callback<GradeDetailDTO>() {
                    @Override
                    public void onResponse(Call<GradeDetailDTO> call,
                                           Response<GradeDetailDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            fillDetails(response.body());
                        } else {
                            Toast.makeText(getContext(),
                                    "Błąd pobierania szczegółów: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GradeDetailDTO> call, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fillDetails(GradeDetailDTO dto) {
        gradeTv.setText(String.valueOf(dto.getGrade()));
        categoryTv.setText("Ocena");
        teacherTv.setText("Nauczyciel: " + dto.getTeacherName());
        typeTv.setText("Typ oceny: " + dto.getType());
        dateTv.setText("Data: " + dto.getIssueDate());
        descriptionTv.setText(dto.getDescription());
    }
}
