package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.RegisterUserRequest;
import com.example.memoriessb.service.AuthService;
import com.example.memoriessb.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Kontroler REST odpowiedzialny za rejestrację, logowanie oraz resetowanie hasła.
 * Udostępnia punkty końcowe związane z autoryzacją i zarządzaniem kontem użytkownika.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    /**
     * Rejestruje nowego użytkownika na podstawie przesłanych danych.
     *
     * @param request dane rejestracyjne użytkownika
     * @return komunikat o powodzeniu lub błędzie rejestracji
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest request) {
        log.debug(">>> POST /api/auth/register payload = {}", request);
        try {
            authService.registerUser(request);
            log.debug("<<< POST /api/auth/register OK");
            return ResponseEntity.ok("Użytkownik został utworzony");
        } catch (Exception ex) {
            log.error("!!! Błąd w registerUser: ", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    /**
     * Loguje użytkownika na podstawie loginu i hasła.
     *
     * @param credentials mapa z kluczami "login" i "password"
     * @return obiekt {@link com.example.memoriessb.DTO.LoginResponse} lub komunikat o błędzie
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            return ResponseEntity.ok(authService.login(
                    credentials.get("login"), credentials.get("password")));
        } catch (Exception ex) {
            log.error("!!! Błąd w login: ", ex);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    /**
     * Wysyła e-mail z linkiem do zresetowania hasła, jeśli login istnieje.
     *
     * @param body mapa zawierająca klucz "login" (adres e-mail)
     * @return komunikat o wysłaniu wiadomości
     */
    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        if (login == null || login.isEmpty()) {
            return ResponseEntity.badRequest().body("Brak loginu (e-maila)");
        }

        passwordResetService.requestPasswordReset(login);
        return ResponseEntity.ok("Jeśli konto istnieje, link resetujący został wysłany.");
    }

    /**
     * Resetuje hasło użytkownika na podstawie tokenu i nowego hasła.
     *
     * @param body mapa zawierająca klucze "token" i "newPassword"
     * @return komunikat o powodzeniu lub błędzie resetu hasła
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Brak tokenu lub hasła");
        }

        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok("Hasło zostało zaktualizowane");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nieprawidłowy lub wygasły token");
        }
    }
}
