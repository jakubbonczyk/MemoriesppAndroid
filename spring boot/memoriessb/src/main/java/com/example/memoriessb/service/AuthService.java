package com.example.memoriessb.service;

import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.DTO.RegisterUserRequest;
import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.SensitiveDataRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SensitiveDataRepository sensitiveDataRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(String login, String password) {
        SensitiveData data = sensitiveDataRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy login"));

        if (!passwordEncoder.matches(password, data.getPassword())) {
            throw new IllegalArgumentException("Nieprawidłowe hasło");
        }

        User user = data.getUser();
        String b64 = user.getImage() == null ? null : Base64.getEncoder().encodeToString(user.getImage());
        return new LoginResponse(user.getId(), user.getName(), user.getSurname(), user.getRole(), b64);
    }

    public void registerUser(RegisterUserRequest request) {
        if (sensitiveDataRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Podany login jest już zajęty");
        }

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setRole(request.getRole());
        userRepository.save(user);

        SensitiveData data = new SensitiveData();
        data.setLogin(request.getLogin());
        data.setPassword(passwordEncoder.encode(request.getPassword()));
        data.setUser(user);
        sensitiveDataRepository.save(data);
    }
}
