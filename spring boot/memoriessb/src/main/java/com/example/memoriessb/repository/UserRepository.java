package com.example.memoriessb.repository;

import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repozytorium JPA dla encji {@link User}.
 * Umożliwia operacje CRUD oraz wyszukiwanie użytkowników według roli.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Zwraca listę użytkowników o określonej roli (np. nauczyciele, uczniowie, administratorzy).
     *
     * @param role rola użytkownika (T – nauczyciel, A – administrator, S – uczeń)
     * @return lista użytkowników o podanej roli
     */
    List<User> findAllByRole(User.Role role);
}
