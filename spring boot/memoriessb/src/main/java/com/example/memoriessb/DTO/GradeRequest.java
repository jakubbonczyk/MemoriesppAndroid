package com.example.memoriessb.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Obiekt DTO reprezentujący żądanie utworzenia nowej oceny.
 * Zawiera dane ucznia, nauczyciela, przedmiotu oraz szczegóły oceny.
 */
@Getter
@Setter
@NoArgsConstructor
public class GradeRequest {

    /** Identyfikator ucznia, któremu wystawiana jest ocena. */
    private Integer studentId;

    /** Identyfikator nauczyciela wystawiającego ocenę. */
    private Integer teacherId;

    /** Identyfikator klasy (przedmiotu), której dotyczy ocena. */
    private Integer classId;

    /** Wartość oceny (np. 5, 4). */
    private Integer grade;

    /** Opcjonalny opis do oceny. */
    private String description;

    /** Typ oceny, np. "sprawdzian", "aktywność". */
    private String type;
}
