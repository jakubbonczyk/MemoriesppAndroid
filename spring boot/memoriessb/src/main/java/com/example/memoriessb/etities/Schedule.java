package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="schedule")
@Getter @Setter

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idschedule")
    private Integer id;

    @Column(name="lesson_date", nullable=false)
    private LocalDate lessonDate;

    @Column(name="start_time", nullable=false)
    private LocalTime startTime;

    @Column(name="end_time", nullable=false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_members_has_class_id", nullable=false)
    private GroupMemberClass groupMemberClass;

}
