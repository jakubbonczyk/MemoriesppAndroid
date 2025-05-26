package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Obiekt DTO reprezentujący odpowiedź zawierającą podstawowe informacje o ocenie.
 * Zawiera identyfikator, wartość oceny, jej opis oraz nazwisko nauczyciela.
 */
@Data
@AllArgsConstructor
public class GradeResponse {

    /** Identyfikator oceny. */
    private int id;

    /** Wartość oceny (np. 4, 5). */
    private int grade;

    /** Opis oceny, np. "praca domowa", "kartkówka". */
    private String description;

    /** Imię i nazwisko nauczyciela, który wystawił ocenę. */
    private String teacherName;
}
