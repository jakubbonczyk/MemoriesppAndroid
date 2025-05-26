package com.example.memoriessb.service;

import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * Serwis odpowiedzialny za operacje na użytkownikach systemu.
 * Umożliwia aktualizację zdjęcia profilowego oraz pobieranie podstawowych danych użytkownika.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    /**
     * Aktualizuje zdjęcie profilowe użytkownika na podstawie przesłanego ciągu Base64.
     *
     * @param userId       identyfikator użytkownika
     * @param base64Image  zakodowany obraz w formacie Base64 (może być null, jeśli usuwamy zdjęcie)
     * @throws IllegalArgumentException jeśli użytkownik nie istnieje lub dekodowanie się nie powiedzie
     */
    public void updateUserImage(Integer userId, String base64Image) {
        log.info("updateUserImage() dla id={} ({} znaków)", userId,
                base64Image == null ? 0 : base64Image.length());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Użytkownik {} nie znaleziony!", userId);
                    return new IllegalArgumentException("User not found");
                });

        try {
            byte[] imageBytes = base64Image == null
                    ? null
                    : Base64.getDecoder().decode(base64Image);
            user.setImage(imageBytes);
            userRepository.save(user);
            log.info("Avatar użytkownika {} zaktualizowany", userId);
        } catch (IllegalArgumentException e) {
            log.error("Błąd dekodowania Base64 dla user={}:", userId, e);
            throw e;
        }
    }

    /**
     * Zwraca podstawowe informacje o użytkowniku w postaci mapy klucz–wartość.
     * Dane mogą być wykorzystane np. do wyświetlania profilu.
     *
     * @param userId identyfikator użytkownika
     * @return mapa zawierająca ID, imię, nazwisko, rolę oraz zdjęcie w formacie Base64
     * @throws IllegalArgumentException jeśli użytkownik nie istnieje
     */
    public java.util.Map<String, Object> getUserDto(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String b64 = user.getImage() == null
                ? null
                : Base64.getEncoder().encodeToString(user.getImage());

        return java.util.Map.of(
                "id",      user.getId(),
                "name",    user.getName(),
                "surname", user.getSurname(),
                "role",    user.getRole().name(),
                "image",   b64
        );
    }
}
