package com.example.memoriessb.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Obiekt DTO reprezentujący żądanie utworzenia nowej grupy użytkowników.
 * Zawiera nazwę grupy przesyłaną przez klienta.
 */
@NoArgsConstructor
@Getter @Setter
public class CreateGroupRequest {

    /** Nazwa tworzonej grupy, np. "Grupa nauczycieli 1". */
    private String groupName;
}
