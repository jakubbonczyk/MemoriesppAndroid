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

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final GroupMemberRepository groupMemberRepo;
    private final UserGroupRepository userGroupRepo;
    private final SensitiveDataRepository sensitiveRepo;

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

    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<GroupDTO>> getGroupsForUser(@PathVariable Integer userId) {
        // najpierw ID grup z group_members
        List<Integer> groupIds = groupMemberRepo
                .findAllByUser_Id(userId)
                .stream()
                .map(gm -> gm.getUserGroup().getId())
                .toList();
        // potem pobierz je i zmapuj na DTO
        List<GroupDTO> dtos = userGroupRepo.findAllById(groupIds)
                .stream()
                .map(g -> new GroupDTO(g.getId(), g.getGroupName()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = userRepo.findAll().stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getRole()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditUserResponse> getUser(@PathVariable int id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));
        SensitiveData sd = sensitiveRepo.findByUser(u)
                .orElseThrow(() -> new IllegalArgumentException("Brak danych wrażliwych"));
        return ResponseEntity.ok(new EditUserResponse(
                u.getId(), sd.getLogin(), u.getName(), u.getSurname(), u.getRole()
        ));
    }

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
