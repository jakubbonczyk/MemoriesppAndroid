package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Obiekt DTO reprezentujący klasę (przedmiot) wraz z jej nazwą i średnią ocen.
 * Używany do prezentacji listy klas oraz analiz wyników uczniów.
 */
@Getter
@Setter
@AllArgsConstructor
public class SchoolClassDTO {

    /** Identyfikator klasy (przedmiotu). */
    private int id;

    /** Nazwa klasy, np. "Matematyka", "1B". */
    private String className;

    /** Średnia ocen w danej klasie. */
    private Double average;
}
