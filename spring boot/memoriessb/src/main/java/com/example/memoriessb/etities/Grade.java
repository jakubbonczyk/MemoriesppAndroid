package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column
    private String type;

    @Column(name = "issue_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate issueDate;


    @ManyToOne
    @JoinColumn(name = "users_idstudent", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "users_idteacher", nullable = false)
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "class_idclass", nullable = false)
    private SchoolClass schoolClass;

    @PrePersist
    public void prePersist() {
        if (issueDate == null) {
            issueDate = LocalDate.now();
        }
    }
}
