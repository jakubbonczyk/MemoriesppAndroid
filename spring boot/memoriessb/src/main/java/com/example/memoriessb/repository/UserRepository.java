package com.example.memoriessb.repository;

import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> { }
