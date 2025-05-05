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

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createLesson(@RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(scheduleService.createLesson(dto));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getScheduleForGroup(
            @PathVariable int groupId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(scheduleService.getScheduleForGroup(groupId, from, to));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getScheduleForTeacher(
            @PathVariable int teacherId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(scheduleService.getScheduleForTeacher(teacherId, from, to));
    }
}
