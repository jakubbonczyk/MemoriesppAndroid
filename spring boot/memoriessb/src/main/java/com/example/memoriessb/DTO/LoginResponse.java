package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;

/**
 * Obiekt DTO reprezentujący odpowiedź po udanym logowaniu użytkownika.
 * Zawiera podstawowe informacje o użytkowniku, w tym imię, nazwisko, rolę i przypisaną klasę.
 *
 * @param id         identyfikator użytkownika
 * @param name       imię użytkownika
 * @param surname    nazwisko użytkownika
 * @param role       rola użytkownika (T – nauczyciel, A – administrator, S – uczeń)
 * @param image      zakodowane zdjęcie profilowe użytkownika (Base64)
 * @param className  nazwa klasy, do której przypisany jest użytkownik (jeśli dotyczy)
 */
public record LoginResponse(
        Integer id,
        String name,
        String surname,
        User.Role role,
        String image,
        String className
) {}
