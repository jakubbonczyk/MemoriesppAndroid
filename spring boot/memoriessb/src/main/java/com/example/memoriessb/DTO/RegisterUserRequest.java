package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.Data;

/**
 * Obiekt DTO reprezentujący żądanie rejestracji nowego użytkownika.
 * Zawiera dane osobowe, dane logowania, rolę oraz identyfikator przypisanej grupy.
 */
@Data
public class RegisterUserRequest {

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Login użytkownika (np. adres e-mail). */
    private String login;

    /** Hasło użytkownika (przed zaszyfrowaniem). */
    private String password;

    /** Rola użytkownika (T – nauczyciel, A – administrator, S – uczeń). */
    private User.Role role;

    /** Identyfikator grupy, do której przypisywany jest użytkownik. */
    private Integer groupId;
}