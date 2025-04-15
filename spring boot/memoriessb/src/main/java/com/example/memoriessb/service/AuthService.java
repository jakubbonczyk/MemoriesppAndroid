package com.example.memoriessb.service;


import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.exception.InvalidCredentialsException;
import com.example.memoriessb.repository.SensitiveDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SensitiveDataRepository sensitiveDataRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse loginOrThrow(String login, String password) {
        SensitiveData sensitiveData = sensitiveDataRepository.findByLogin(login)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid login or password"));

        if (!passwordEncoder.matches(password, sensitiveData.getPassword())) {
            throw new InvalidCredentialsException("Invalid login or password");
        }

        User user = sensitiveData.getUser();

        return new LoginResponse(user.getId(), user.getName(), user.getSurname(), user.getRole());
    }


}
