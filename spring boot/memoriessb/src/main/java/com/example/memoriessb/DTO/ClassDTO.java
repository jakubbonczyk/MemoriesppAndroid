package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Obiekt DTO reprezentujący klasę (przedmiot) w systemie.
 * Używany do przesyłania podstawowych informacji o klasie.
 */
@Getter
@AllArgsConstructor
public class ClassDTO {

    /** Identyfikator klasy. */
    private Integer id;

    /** Nazwa klasy, np. "Fizyka", "1A". */
    private String className;
}
