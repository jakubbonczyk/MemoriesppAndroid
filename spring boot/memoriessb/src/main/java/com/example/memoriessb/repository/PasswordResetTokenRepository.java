package com.example.memoriessb.repository;

import com.example.memoriessb.etities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repozytorium JPA dla encji {@link PasswordResetToken}.
 * Umożliwia operacje na tokenach służących do resetowania hasła.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Zwraca token resetowania hasła na podstawie jego wartości.
     *
     * @param token wartość tokenu
     * @return opcjonalny obiekt {@link PasswordResetToken}, jeśli istnieje
     */
    Optional<PasswordResetToken> findByToken(String token);
}
