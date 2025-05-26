package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Encja przechowująca wrażliwe dane logowania użytkownika.
 * Zawiera login, zaszyfrowane hasło oraz powiązanie z użytkownikiem.
 */
@Entity
@Table(name = "sensitive_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveData {

    /** Unikalny identyfikator rekordu danych wrażliwych. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsensitive_data")
    private Integer id;

    /** Login użytkownika (np. e-mail lub nazwa użytkownika). */
    @Column(nullable = false)
    private String login;

    /** Zaszyfrowane hasło użytkownika. */
    @Column(nullable = false, length = 256)
    private String password;

    /** Powiązany użytkownik, do którego należą dane logowania. */
    @OneToOne
    @JoinColumn(name = "users_idusers", nullable = false)
    private User user;
}
