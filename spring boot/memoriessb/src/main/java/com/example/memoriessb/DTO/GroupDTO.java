package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Obiekt DTO reprezentujący grupę użytkowników.
 * Używany do przesyłania podstawowych informacji o grupie.
 */
@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {

    /** Identyfikator grupy. */
    private Integer id;

    /** Nazwa grupy, np. "Grupa nauczycieli 2". */
    private String groupName;
}
