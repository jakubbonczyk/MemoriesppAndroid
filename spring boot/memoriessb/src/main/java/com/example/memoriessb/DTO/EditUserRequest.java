package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Obiekt DTO reprezentujący żądanie edycji danych użytkownika.
 * Zawiera podstawowe dane osobowe, login oraz rolę użytkownika.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditUserRequest {

    /** Login użytkownika (np. e-mail). */
    private String login;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Nowa rola użytkownika (T – nauczyciel, A – administrator, S – uczeń). */
    private User.Role role;
}
