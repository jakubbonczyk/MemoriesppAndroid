package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgrades")
    private Integer id;

    @Column(nullable = false)
    private Integer grade;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "users_idstudent", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "users_idteacher", nullable = false)
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "class_idclass", nullable = false)
    private SchoolClass schoolClass;
}
