package com.example.memoriessb.DTO;

/**
 * Obiekt DTO reprezentujący grupę nauczycieli.
 * Zawiera identyfikator grupy oraz jej nazwę.
 *
 * @param id         identyfikator grupy
 * @param groupName  nazwa grupy nauczycieli
 */
public record TeacherGroupDTO(
        Integer id,
        String groupName
) {}
