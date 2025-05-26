package com.example.memoriessb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serwis odpowiedzialny za wysyłanie wiadomości e-mail.
 * Używany głównie do obsługi resetowania hasła.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Wysyła wiadomość e-mail z linkiem do zresetowania hasła.
     *
     * @param to    adres e-mail odbiorcy
     * @param token unikalny token do resetu hasła, który zostanie osadzony w linku
     */
    public void sendPasswordResetEmail(String to, String token) {
        String url = "http://localhost:8080/reset-password.html?token=" + token;
        String subject = "Resetowanie hasła";
        String text = "Kliknij poniższy link, aby zresetować hasło:\n" + url;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
