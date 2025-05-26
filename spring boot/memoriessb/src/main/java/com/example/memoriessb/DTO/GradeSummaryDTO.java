package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Obiekt DTO zawierający skrócone informacje o ocenie.
 * Używany do prezentacji listy ocen ucznia w uproszczonej formie.
 */
@AllArgsConstructor
@Getter
public class GradeSummaryDTO {

    /** Identyfikator oceny. */
    private Integer id;

    /** Wartość oceny (np. 3, 4, 5). */
    private Integer grade;

    /** Typ oceny, np. "sprawdzian", "kartkówka". */
    private String type;

    /** Data wystawienia oceny (w formacie tekstowym). */
    private String issueDate;
}
