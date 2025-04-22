package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;

public record UserDTO(Integer id, String name, String surname, User.Role role) {}
