package com.example.memoriessb.repository;

import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(User.Role role);

}
