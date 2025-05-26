package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.EditUserRequest;
import com.example.memoriessb.DTO.EditUserResponse;
import com.example.memoriessb.DTO.GroupDTO;
import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.etities.SensitiveData;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.repository.SensitiveDataRepository;
import com.example.memoriessb.repository.UserGroupRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Kontroler REST odpowiedzialny za zarządzanie użytkownikami systemu.
 * Umożliwia pobieranie, edytowanie i przypisywanie użytkowników do grup oraz aktualizację zdjęć profilowych.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final GroupMemberRepository groupMemberRepo;
    private final UserGroupRepository userGroupRepo;
    private final SensitiveDataRepository sensitiveRepo;

    /**
     * Aktualizuje zdjęcie profilowe użytkownika na podstawie zakodowanego ciągu Base64.
     *
     * @param id   identyfikator użytkownika
     * @param body mapa z kluczem "image" zawierającym dane Base64
     * @return odpowiedź HTTP 200 OK lub 400 Bad Request
     */
    @PutMapping("/{id}/profile-image")
    public ResponseEntity<Void> uploadProfileImage(
            @PathVariable int id,
            @RequestBody Map<String,String> body
    ) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        String b64 = body.get("image");
        if (b64 == null || b64.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(b64);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        u.setImage(imageBytes);
        userRepo.save(u);
        return ResponseEntity.ok().build();
    }

    /**
     * Zwraca listę wszystkich użytkowników z rolą nauczyciela.
     *
     * @return lista nauczycieli w postaci {@link UserDTO}
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<UserDTO>> getAllTeachers() {
        List<UserDTO> teachers = userRepo.findAll().stream()
                .filter(u -> u.getRole() == User.Role.T)
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getName(),
                        u.getSurname(),
                        u.getRole()))
                .toList();
        return ResponseEntity.ok(teachers);
    }

    /**
     * Zwraca listę grup, do których przypisany jest dany użytkownik.
     *
     * @param userId identyfikator użytkownika
     * @return lista grup {@link GroupDTO}
     */
    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<GroupDTO>> getGroupsForUser(@PathVariable Integer userId) {
        List<Integer> groupIds = groupMemberRepo
                .findAllByUser_Id(userId)
                .stream()
                .map(gm -> gm.getUserGroup().getId())
                .toList();
        List<GroupDTO> dtos = userGroupRepo.findAllById(groupIds)
                .stream()
                .map(g -> new GroupDTO(g.getId(), g.getGroupName()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Zwraca listę wszystkich użytkowników w systemie.
     *
     * @return lista użytkowników w postaci {@link UserDTO}
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = userRepo.findAll().stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getRole()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Zwraca dane użytkownika do edycji, wraz z loginem i zakodowanym zdjęciem profilowym.
     *
     * @param id identyfikator użytkownika
     * @return dane użytkownika w postaci {@link EditUserResponse}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EditUserResponse> getUser(@PathVariable int id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));
        SensitiveData sd = sensitiveRepo.findByUser(u)
                .orElseThrow(() -> new IllegalArgumentException("Brak danych wrażliwych"));

        String base64 = u.getImage() != null
                ? Base64.getEncoder().encodeToString(u.getImage())
                : "";

        return ResponseEntity.ok(new EditUserResponse(
                u.getId(),
                sd.getLogin(),
                u.getName(),
                u.getSurname(),
                u.getRole(),
                base64
        ));
    }

    /**
     * Aktualizuje dane użytkownika (imię, nazwisko, rola, login).
     *
     * @param id  identyfikator użytkownika
     * @param req dane do aktualizacji {@link EditUserRequest}
     * @return komunikat o powodzeniu
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable int id,
            @RequestBody EditUserRequest req
    ) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));
        u.setName(req.getName());
        u.setSurname(req.getSurname());
        u.setRole(req.getRole());
        userRepo.save(u);

        SensitiveData sd = sensitiveRepo.findByUser(u)
                .orElseThrow(() -> new IllegalArgumentException("Brak danych wrażliwych"));
        sd.setLogin(req.getLogin());
        sensitiveRepo.save(sd);

        return ResponseEntity.ok("Użytkownik zaktualizowany");
    }
}
