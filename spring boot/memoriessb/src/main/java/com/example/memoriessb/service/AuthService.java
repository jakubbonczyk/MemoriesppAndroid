package com.example.memoriessb.service;

import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.SensitiveDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SensitiveDataRepository sensitiveDataRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(String login, String password) {
        SensitiveData data = sensitiveDataRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy login"));

        if (!passwordEncoder.matches(password, data.getPassword())) {
            throw new IllegalArgumentException("Nieprawidłowe hasło");
        }

        User user = data.getUser();
        return new LoginResponse(user.getId(), user.getName(), user.getSurname(), user.getRole());
    }
}
