package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Obiekt DTO reprezentujący nowo dodaną ocenę.
 * Używany do przesyłania informacji o wystawionej ocenie wraz z kontekstem przedmiotu.
 */
@AllArgsConstructor
@Getter
public class NewGradeDTO {

    /** Identyfikator nowej oceny. */
    private Integer id;

    /** Wartość oceny (np. 5, 4). */
    private Integer grade;

    /** Typ oceny, np. "kartkówka", "sprawdzian". */
    private String type;

    /** Data wystawienia oceny w formacie tekstowym. */
    private String issueDate;

    /** Nazwa klasy (przedmiotu), z której wystawiono ocenę. */
    private String className;
}
