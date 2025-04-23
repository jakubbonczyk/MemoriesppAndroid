package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// GroupController.java
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;

    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<UserDTO>> getStudentsInGroup(@PathVariable int groupId) {
        List<Integer> userIds = groupMemberRepo
                .findAllByGroup_Id(groupId)
                .stream()
                .map(gm -> gm.getUser().getId())
                .toList();

        List<UserDTO> students = userRepo.findAllById(userIds)
                .stream()
                .filter(u -> u.getRole() == User.Role.S)
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(),u.getRole()))
                .toList();

        return ResponseEntity.ok(students);
    }
}
