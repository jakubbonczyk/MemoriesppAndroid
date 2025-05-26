package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Obiekt DTO służący do przesyłania informacji o przypisaniu nauczyciela do przedmiotu.
 * Zawiera identyfikator przypisania, dane nauczyciela oraz informacje o klasie.
 */
@Data
@AllArgsConstructor
public class AssignmentDTO {

    /** Identyfikator przypisania (relacji nauczyciel–klasa). */
    private Integer assignmentId;

    /** Imię i nazwisko nauczyciela. */
    private String teacherName;

    /** Identyfikator klasy (przedmiotu). */
    private Integer classId;

    /** Nazwa klasy (przedmiotu), np. "Matematyka", "1B". */
    private String className;
}
