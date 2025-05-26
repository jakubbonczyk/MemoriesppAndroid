package com.example.memoriessb.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Obiekt DTO reprezentujący żądanie utworzenia nowej klasy (przedmiotu).
 * Zawiera nazwę klasy przesyłaną przez klienta.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateClassRequest {

    /** Nazwa tworzonej klasy, np. "Biologia", "2B". */
    private String className;
}
