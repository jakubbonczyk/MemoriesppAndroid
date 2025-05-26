package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.ScheduleRequestDTO;
import com.example.memoriessb.DTO.ScheduleResponseDTO;
import com.example.memoriessb.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Kontroler REST odpowiedzialny za zarządzanie harmonogramem zajęć.
 * Umożliwia tworzenie lekcji oraz pobieranie planu dla nauczycieli i grup.
 */
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * Tworzy nową lekcję i generuje kolejne powtórzenia lekcji w tym samym dniu tygodnia,
     * o ile mieszczą się w tym samym miesiącu.
     *
     * @param dto dane lekcji (data, godziny, przypisanie nauczyciela do przedmiotu)
     * @return szczegóły utworzonej lekcji {@link ScheduleResponseDTO}
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createLesson(@RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleService.createLesson(dto));
    }

    /**
     * Zwraca plan zajęć dla danej grupy w zadanym przedziale dat.
     *
     * @param groupId identyfikator grupy
     * @param from    data początkowa (format ISO: YYYY-MM-DD)
     * @param to      data końcowa (format ISO: YYYY-MM-DD)
     * @return lista lekcji w postaci {@link ScheduleResponseDTO}
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getScheduleForGroup(
            @PathVariable int groupId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(scheduleService.getScheduleForGroup(groupId, from, to));
    }

    /**
     * Zwraca plan zajęć prowadzonych przez danego nauczyciela w zadanym przedziale dat.
     *
     * @param teacherId identyfikator nauczyciela
     * @param from      data początkowa (format ISO: YYYY-MM-DD)
     * @param to        data końcowa (format ISO: YYYY-MM-DD)
     * @return lista lekcji w postaci {@link ScheduleResponseDTO}
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getScheduleForTeacher(
            @PathVariable int teacherId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(scheduleService.getScheduleForTeacher(teacherId, from, to));
    }
}
