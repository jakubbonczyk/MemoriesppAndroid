package com.example.memoriessb.repository;

import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensitiveDataRepository extends JpaRepository<SensitiveData, Integer> {
    Optional<SensitiveData> findByLogin(String login);
    Optional<SensitiveData> findByUser(User user);
}