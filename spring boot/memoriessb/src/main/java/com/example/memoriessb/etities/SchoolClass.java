package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Encja reprezentująca przedmiot lub klasę w systemie.
 * Może być przypisana do nauczycieli oraz powiązana z ocenami i planem zajęć.
 */
@Entity
@Table(name = "class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClass {

    /** Unikalny identyfikator klasy/przedmiotu. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclass")
    private Integer id;

    /** Nazwa klasy lub przedmiotu, np. "Matematyka", "1A". */
    @Column(name = "class_name", nullable = false)
    private String className;
}
