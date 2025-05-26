package com.example.memoriessb.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Obiekt DTO reprezentujący żądanie dodania wpisu do planu zajęć.
 * Zawiera informacje o przypisaniu nauczyciela do przedmiotu, dacie oraz godzinach lekcji.
 */
@Data
public class ScheduleRequestDTO {

    /** Identyfikator przypisania nauczyciela do przedmiotu. */
    private Integer assignmentId;

    /** Data lekcji. */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lessonDate;

    /** Czas rozpoczęcia lekcji. */
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    /** Czas zakończenia lekcji. */
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;
}
