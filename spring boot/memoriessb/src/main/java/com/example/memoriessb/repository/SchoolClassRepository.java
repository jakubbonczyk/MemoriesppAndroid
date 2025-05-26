package com.example.memoriessb.repository;

import com.example.memoriessb.etities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium JPA dla encji {@link SchoolClass}.
 * Umożliwia podstawowe operacje CRUD na klasach (przedmiotach).
 */
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer> {
}
