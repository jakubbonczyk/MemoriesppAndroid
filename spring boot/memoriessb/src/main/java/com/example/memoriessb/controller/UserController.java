package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/group/{groupId}/students")
    public List<UserDTO> getStudentsByGroup(@PathVariable Integer groupId) {
        return userRepository.findStudentsByGroupId(groupId).stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getRole()))
                .toList();
    }
}
