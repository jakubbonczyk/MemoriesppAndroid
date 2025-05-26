package com.example.memoriessb.repository;

import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repozytorium JPA dla encji {@link SensitiveData}.
 * Odpowiada za operacje na wrażliwych danych logowania użytkowników (login, hasło).
 */
public interface SensitiveDataRepository extends JpaRepository<SensitiveData, Integer> {

    /**
     * Wyszukuje dane logowania na podstawie loginu.
     *
     * @param login login użytkownika (np. e-mail)
     * @return opcjonalny obiekt {@link SensitiveData}, jeśli znaleziono
     */
    Optional<SensitiveData> findByLogin(String login);

    /**
     * Wyszukuje dane logowania powiązane z konkretnym użytkownikiem.
     *
     * @param user obiekt użytkownika
     * @return opcjonalny obiekt {@link SensitiveData}, jeśli znaleziono
     */
    Optional<SensitiveData> findByUser(User user);
}