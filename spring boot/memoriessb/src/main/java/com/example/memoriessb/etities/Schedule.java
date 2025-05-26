package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Encja reprezentująca pojedynczy wpis w planie zajęć.
 * Zawiera informacje o dacie i godzinach lekcji oraz powiązaniu z przedmiotem i nauczycielem.
 */
@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {

    /** Unikalny identyfikator wpisu w planie. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idschedule")
    private Integer id;

    /** Data lekcji. */
    @Column(name = "lesson_date", nullable = false)
    private LocalDate lessonDate;

    /** Czas rozpoczęcia lekcji. */
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    /** Czas zakończenia lekcji. */
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /** Powiązanie z nauczycielem i klasą (przedmiotem). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_members_has_class_id", nullable = false)
    private GroupMemberClass groupMemberClass;

    /** Flaga informująca, czy wpis został wygenerowany automatycznie. */
    @Column(name = "generated", nullable = false)
    private Boolean generated = false;
}
