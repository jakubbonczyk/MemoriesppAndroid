package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.LoginResponse;
import com.example.memoriessb.DTO.RegisterUserRequest;
import com.example.memoriessb.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest request) {
        log.debug(">>> POST /api/auth/register payload = {}", request);
        try {
            authService.registerUser(request);
            log.debug("<<< POST /api/auth/register OK");
            return ResponseEntity.ok("Użytkownik został utworzony");
        } catch (Exception ex) {
            log.error("!!! Błąd w registerUser: ", ex);
            // odsyłamy kod 500 *i* message wyjątku w ciele
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

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
}
