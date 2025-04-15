package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclass")
    private Integer id;

    @Column(name = "class_name", nullable = false)
    private String className;

    @OneToMany(mappedBy = "schoolClass")
    private List<Grade> grades;

    @OneToMany(mappedBy = "schoolClass")
    private List<Schedule> schedules;
}
