package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.GroupDTO;
import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GroupMemberRepository;
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
}
