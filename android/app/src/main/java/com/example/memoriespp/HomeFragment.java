package com.example.memoriespp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import network.GroupApi;
import network.GroupResponse;
import network.ScheduleApi;
import network.ScheduleResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private LinearLayout scheduleContainer;
    private ScheduleApi scheduleApi;
    private GroupApi groupApi;
    private int userId, groupId;
    private String role;
    private FrameLayout teachersFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView12 = rootView.findViewById(R.id.dateNow);
        Calendar calendar = Calendar.getInstance();
        String[] dniTygodnia = {"niedziela","poniedziałek","wtorek","środa","czwartek","piątek","sobota"};
        int dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String currentDate = new java.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                .format(calendar.getTime());
        textView12.setText("Dziś jest " + dniTygodnia[dayOfWeekIndex] + ", " + currentDate);

        rootView.findViewById(R.id.gradesButton).setOnClickListener(v -> {
            BottomNavigationView nav = requireActivity().findViewById(R.id.bottom_navigation);
            nav.setSelectedItemId(R.id.grades);
        });
        rootView.findViewById(R.id.classesButton).setOnClickListener(v -> {
            ClassesFragment classesFragment = new ClassesFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, classesFragment)
                    .addToBackStack(null)
                    .commit();
        });
        rootView.findViewById(R.id.teachersButton).setOnClickListener(v -> {
            TeachersFragment tf = new TeachersFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, tf)
                    .addToBackStack(null)
                    .commit();
        });

        teachersFrameLayout = rootView.findViewById(R.id.teacherLayout);
        if (getArguments() != null) {
            role = getArguments().getString("role");
            userId = getArguments().getInt("userId", -1);
        }
        teachersFrameLayout.setVisibility("T".equals(role) ? View.GONE : View.VISIBLE);

        scheduleContainer = rootView.findViewById(R.id.scheduleContainer);
        Retrofit rt = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        scheduleApi = rt.create(ScheduleApi.class);
        groupApi = rt.create(GroupApi.class);

        if (userId != -1) {
            groupApi.getGroupsForUser(userId).enqueue(new Callback<List<GroupResponse>>() {
                @Override
                public void onResponse(Call<List<GroupResponse>> call, Response<List<GroupResponse>> response) {
                    if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                        groupId = response.body().get(0).getId();
                        loadTodaySchedule();
                    }
                }
                @Override
                public void onFailure(Call<List<GroupResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), "Błąd ładowania grupy", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return rootView;
    }


    private void loadTodaySchedule() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        scheduleContainer.removeAllViews();
        scheduleApi.getScheduleForGroup(groupId, today, today)
                .enqueue(new Callback<List<ScheduleResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<ScheduleResponseDTO>> call,
                                           Response<List<ScheduleResponseDTO>> resp) {
                        if (!resp.isSuccessful()) {
                            Log.e("Schedule", "HTTP error code: " + resp.code());
                            Toast.makeText(getContext(), "Błąd HTTP: " + resp.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<ScheduleResponseDTO> body = resp.body();
                        if (body == null || body.isEmpty()) {
                            Log.d("Schedule", "Brak lekcji na dziś dla grupy " + groupId);
                            Toast.makeText(getContext(), "Brak lekcji dziś", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("Schedule", "Pobrano " + body.size() + " lekcji");
                        LayoutInflater inf = LayoutInflater.from(getContext());
                        for (ScheduleResponseDTO dto : body) {
                            View item = inf.inflate(R.layout.item_home_lesson, scheduleContainer, false);
                            ((TextView)item.findViewById(R.id.tvSubject)).setText(dto.getClassName());
                            ((TextView)item.findViewById(R.id.tvTeacher)).setText(dto.getTeacherName());
                            ((TextView)item.findViewById(R.id.tvTime)).setText(
                                    dto.getStartTime().substring(0,5) + " – " + dto.getEndTime().substring(0,5)
                            );
                            scheduleContainer.addView(item);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ScheduleResponseDTO>> call, Throwable t) {
                        Log.e("Schedule", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Błąd sieci: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
