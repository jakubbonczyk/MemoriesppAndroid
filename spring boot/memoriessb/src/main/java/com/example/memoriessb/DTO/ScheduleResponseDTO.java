package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Obiekt DTO reprezentujący odpowiedź z informacjami o zaplanowanej lekcji.
 * Zawiera datę, godziny lekcji, nazwisko nauczyciela, nazwę przedmiotu oraz grupy.
 */
@Data
@AllArgsConstructor
public class ScheduleResponseDTO {

    /** Identyfikator wpisu w planie zajęć. */
    private Integer id;

    /** Identyfikator przypisania nauczyciela do przedmiotu. */
    private Integer assignmentId;

    /** Data zaplanowanej lekcji. */
    private LocalDate lessonDate;

    /** Godzina rozpoczęcia lekcji. */
    private LocalTime startTime;

    /** Godzina zakończenia lekcji. */
    private LocalTime endTime;

    /** Imię i nazwisko nauczyciela prowadzącego lekcję. */
    private String teacherName;

    /** Nazwa przedmiotu. */
    private String subjectName;

    /** Nazwa grupy, której dotyczy lekcja. */
    private String groupName;
}
