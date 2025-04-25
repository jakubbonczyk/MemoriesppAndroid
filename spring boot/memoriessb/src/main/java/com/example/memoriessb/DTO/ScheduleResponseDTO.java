package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Integer id;
    private Integer assignmentId;
    private LocalDate lessonDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String teacherName;
    private String className;
}
