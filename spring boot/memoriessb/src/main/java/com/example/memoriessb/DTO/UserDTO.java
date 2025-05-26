package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;

/**
 * Obiekt DTO reprezentujący podstawowe informacje o użytkowniku.
 * Zawiera identyfikator, imię, nazwisko oraz rolę użytkownika.
 *
 * @param id       identyfikator użytkownika
 * @param name     imię użytkownika
 * @param surname  nazwisko użytkownika
 * @param role     rola użytkownika (T – nauczyciel, A – administrator, S – uczeń)
 */
public record UserDTO(Integer id, String name, String surname, User.Role role) {}
