package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Encja reprezentująca ocenę przypisaną uczniowi.
 * Zawiera informacje o wartości oceny, jej typie, dacie wystawienia,
 * powiązanym uczniu, nauczycielu oraz przedmiocie (klasie).
 */
@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    /** Unikalny identyfikator oceny. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgrades")
    private Integer id;

    /** Wartość oceny (np. 4, 5). */
    @Column(nullable = false)
    private Integer grade;

    /** Opcjonalny opis do oceny. */
    @Column
    private String description;

    /** Typ oceny, np. "sprawdzian", "kartkówka", "aktywność". */
    @Column
    private String type;

    /** Data wystawienia oceny. */
    @Column(name = "issue_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate issueDate;

    /** Uczeń, któremu przypisana jest ocena. */
    @ManyToOne
    @JoinColumn(name = "users_idstudent", nullable = false)
    private User student;

    /** Nauczyciel, który wystawił ocenę. */
    @ManyToOne
    @JoinColumn(name = "users_idteacher", nullable = false)
    private User teacher;

    /** Przedmiot (klasa), z którego wystawiono ocenę. */
    @ManyToOne
    @JoinColumn(name = "class_idclass", nullable = false)
    private SchoolClass schoolClass;

    /**
     * Ustawia bieżącą datę jako datę wystawienia oceny, jeśli nie została wcześniej ustawiona.
     */
    @PrePersist
    public void prePersist() {
        if (issueDate == null) {
            issueDate = LocalDate.now();
        }
    }

    /** Flaga informująca, czy uczeń został powiadomiony o ocenie. */
    @Column(nullable = false)
    private boolean notified = false;
}
