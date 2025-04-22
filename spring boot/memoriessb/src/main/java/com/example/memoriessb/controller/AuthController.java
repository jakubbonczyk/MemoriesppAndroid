package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Map<String, String> credentials) {
        return authService.login(credentials.get("login"), credentials.get("password"));
    }

}
