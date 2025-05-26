package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Obiekt DTO zawierający szczegółowe informacje o ocenie.
 * Przeznaczony do prezentacji pełnych danych o pojedynczej ocenie,
 * w tym osoby ocenianej, nauczyciela i przypisanego przedmiotu.
 */
@AllArgsConstructor
@Getter
@Setter
public class GradeDetailDTO {

    /** Identyfikator oceny. */
    private Integer id;

    /** Wartość oceny (np. 5, 4). */
    private Integer grade;

    /** Typ oceny, np. "kartkówka", "sprawdzian". */
    private String type;

    /** Data wystawienia oceny (w formacie tekstowym). */
    private String issueDate;

    /** Opcjonalny opis oceny. */
    private String description;

    /** Imię i nazwisko ucznia. */
    private String studentName;

    /** Imię i nazwisko nauczyciela. */
    private String teacherName;

    /** Nazwa klasy (przedmiotu), do której przypisana jest ocena. */
    private String className;
}
