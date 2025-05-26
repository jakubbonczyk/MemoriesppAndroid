package com.example.memoriessb.etities;

import com.example.memoriessb.etities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Encja reprezentująca token do resetowania hasła użytkownika.
 * Przechowuje wartość tokenu, powiązanego użytkownika oraz datę wygaśnięcia.
 */
@Entity
@Table(name = "password_reset_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    /** Unikalny identyfikator tokenu. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Wartość tokenu, unikalna dla każdego resetu hasła. */
    @Column(nullable = false, unique = true)
    private String token;

    /** Użytkownik, dla którego wygenerowano token. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Data i godzina wygaśnięcia tokenu. */
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
