package com.example.memoriespp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

/**
 * Fragment wyświetlający plan zajęć dla aktualnie zalogowanego użytkownika
 * (ucznia lub nauczyciela) na wybrany dzień. Umożliwia przełączanie między dniami,
 * a także dostęp do ocen.
 */
public class HomeFragment extends Fragment {
    private LinearLayout scheduleContainer;
    private ScheduleApi  scheduleApi;
    private GroupApi     groupApi;
    private TextView     dateNow;
    private ImageButton  btnPrevDay, btnNextDay, gradesButton;
    private LocalDate    displayedDate;
    private int userId, groupId;
    private String role;

    /**
     * Inicjalizuje widok fragmentu, ustawia przyciski do zmiany dnia oraz przycisk ocen.
     * Automatycznie ładuje plan zajęć dla bieżącego dnia.
     *
     * @param inflater  obiekt LayoutInflater do tworzenia widoku
     * @param container kontener, do którego widok zostanie dołączony
     * @param savedInstanceState poprzedni zapisany stan (jeśli istnieje)
     * @return główny widok fragmentu
     */
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        dateNow          = root.findViewById(R.id.dateNow);
        btnPrevDay       = root.findViewById(R.id.btnPrevDay);
        btnNextDay       = root.findViewById(R.id.btnNextDay);
        scheduleContainer= root.findViewById(R.id.scheduleContainer);
        gradesButton = root.findViewById(R.id.gradesButton);

        gradesButton.setOnClickListener(v -> {
            GradesFragment frag = new GradesFragment();
            Bundle args = new Bundle();
            args.putInt("userId", userId);
            args.putString("role", role);
            frag.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, frag)
                    .addToBackStack(null)
                    .commit();
        });


        if (getArguments() != null) {
            role   = getArguments().getString("role");
            userId = getArguments().getInt("userId", -1);
        }

        Retrofit rt = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        scheduleApi = rt.create(ScheduleApi.class);
        groupApi    = rt.create(GroupApi   .class);

        displayedDate = LocalDate.now();
        updateDateLabel();
        loadScheduleFor(displayedDate);

        btnPrevDay.setOnClickListener(v -> {
            displayedDate = displayedDate.minusDays(1);
            updateDateLabel();
            loadScheduleFor(displayedDate);
        });
        btnNextDay.setOnClickListener(v -> {
            displayedDate = displayedDate.plusDays(1);
            updateDateLabel();
            loadScheduleFor(displayedDate);
        });

        return root;
    }

    /**
     * Aktualizuje nagłówek daty, wyświetlając dzień tygodnia oraz datę
     * w formacie dd.MM.yyyy.
     */
    private void updateDateLabel() {
        Calendar cal = Calendar.getInstance();
        cal.set(displayedDate.getYear(),
                displayedDate.getMonthValue() - 1,
                displayedDate.getDayOfMonth());
        String[] dni = {
                "niedziela","poniedziałek","wtorek",
                "środa","czwartek","piątek","sobota"
        };
        int idx = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String data = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                .format(cal.getTime());
        dateNow.setText("Dzień: " + dni[idx] + ", " + data);
    }

    /**
     * Ładuje plan zajęć dla danego dnia na podstawie roli użytkownika (uczeń lub nauczyciel).
     *
     * @param date data, dla której ma zostać załadowany plan zajęć
     */
    private void loadScheduleFor(LocalDate date) {
        String iso = date.format(DateTimeFormatter.ISO_DATE);
        scheduleContainer.removeAllViews();

        if ("S".equals(role)) {
            groupApi.getGroupsForUser(userId)
                    .enqueue(new Callback<List<GroupResponse>>() {
                        @Override public void onResponse(Call<List<GroupResponse>> c,
                                                         Response<List<GroupResponse>> r) {
                            if (!r.isSuccessful() || r.body()==null || r.body().isEmpty()) return;
                            groupId = r.body().get(0).getId();
                            fetchAndShow((api,f,t)->api.getScheduleForGroup(groupId,f,t),
                                    iso, iso);
                        }
                        @Override public void onFailure(Call<List<GroupResponse>> c, Throwable t) {
                            Toast.makeText(getContext(),
                                    "Błąd ładowania grupy", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if ("T".equals(role)) {
            fetchAndShow((api,f,t)->api.getScheduleForTeacher(userId,f,t),
                    iso, iso);
        }
    }

    /**
     * Wykonuje zapytanie do API i wyświetla pobrane zajęcia w layoucie.
     *
     * @param loader funkcjonalny interfejs służący do załadowania danych z API
     * @param from   data początkowa (w formacie ISO)
     * @param to     data końcowa (w formacie ISO)
     */
    private void fetchAndShow(ScheduleLoader loader,
                              String from, String to) {
        loader.load(scheduleApi, from, to)
                .enqueue(new Callback<List<ScheduleResponseDTO>>() {
                    @Override public void onResponse(Call<List<ScheduleResponseDTO>> c,
                                                     Response<List<ScheduleResponseDTO>> r) {
                        if (!r.isSuccessful() || r.body()==null) return;
                        for (ScheduleResponseDTO dto : r.body()) {
                            View item = LayoutInflater.from(getContext())
                                    .inflate(R.layout.item_home_lesson,
                                            scheduleContainer, false);

                            TextView tv1 = item.findViewById(R.id.tvSubject);
                            TextView tv2 = item.findViewById(R.id.tvTeacher);
                            TextView tv3 = item.findViewById(R.id.tvTime);

                            if ("S".equals(role)) {
                                tv1.setText(dto.getSubjectName());
                                tv2.setText(dto.getTeacherName());
                            } else {
                                tv1.setText(dto.getSubjectName());
                                tv2.setText(dto.getGroupName());
                            }

                            String times = dto.getStartTime().substring(0,5)
                                    + " – " + dto.getEndTime().substring(0,5);
                            tv3.setText(times);

                            scheduleContainer.addView(item);
                        }
                    }
                    @Override public void onFailure(Call<List<ScheduleResponseDTO>> c, Throwable t) {
                        Toast.makeText(getContext(),
                                "Błąd sieci", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Funkcjonalny interfejs służący do ładowania harmonogramu zajęć z API.
     */
    private interface ScheduleLoader {
        Call<List<ScheduleResponseDTO>> load(
                ScheduleApi api, String from, String to
        );
    }
}
