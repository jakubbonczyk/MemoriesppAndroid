package com.example.memoriessb.DTO;


import com.example.memoriessb.etities.User;

public record LoginResponse(
        Integer id,
        String name,
        String surname,
        User.Role role,
        String image,
        String className
) {}