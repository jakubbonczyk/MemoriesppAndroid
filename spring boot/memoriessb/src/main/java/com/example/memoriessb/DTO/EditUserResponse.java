package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Obiekt DTO reprezentujący odpowiedź po edycji danych użytkownika.
 * Zawiera zaktualizowane dane osobowe, login, rolę oraz zakodowany obraz profilowy.
 */
@AllArgsConstructor
@Getter
public class EditUserResponse {

    /** Identyfikator użytkownika. */
    private Integer id;

    /** Login użytkownika (np. e-mail). */
    private String login;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Rola użytkownika po edycji. */
    private User.Role role;

    /** Zdjęcie profilowe zakodowane jako ciąg Base64. */
    String image;
}
