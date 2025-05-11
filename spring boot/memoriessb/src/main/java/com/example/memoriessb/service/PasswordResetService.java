package com.example.memoriessb.service;

import com.example.memoriessb.etities.PasswordResetToken;
import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.PasswordResetTokenRepository;
import com.example.memoriessb.repository.SensitiveDataRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final SensitiveDataRepository sensitiveDataRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void requestPasswordReset(String login) {
        Optional<SensitiveData> sensitiveOpt = sensitiveDataRepository.findByLogin(login);
        if (sensitiveOpt.isEmpty()) return;

        SensitiveData sensitiveData = sensitiveOpt.get();
        User user = sensitiveData.getUser();

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiry);
        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(login, token);
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) return false;

        PasswordResetToken resetToken = tokenOpt.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            return false;
        }

        User user = resetToken.getUser();
        SensitiveData sensitiveData = sensitiveDataRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Brak danych logowania"));

        String hashedPassword = passwordEncoder.encode(newPassword);
        sensitiveData.setPassword(hashedPassword);
        sensitiveDataRepository.save(sensitiveData);

        tokenRepository.delete(resetToken);

        return true;
    }
}
