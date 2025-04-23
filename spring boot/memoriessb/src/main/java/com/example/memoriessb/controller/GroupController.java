package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.CreateGroupRequest;
import com.example.memoriessb.DTO.GroupDTO;
import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.etities.UserGroup;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.repository.UserGroupRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;
    private final UserGroupRepository groupRepo;

    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<UserDTO>> getStudentsInGroup(@PathVariable int groupId) {
        List<Integer> userIds = groupMemberRepo
                .findAllByUserGroup_Id(groupId)    // <–– tu
                .stream()
                .map(gm -> gm.getUser().getId())
                .toList();

        List<UserDTO> students = userRepo.findAllById(userIds)
                .stream()
                .filter(u -> u.getRole() == User.Role.S)
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getRole()))
                .toList();

        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupRequest req) {
        UserGroup g = new UserGroup();
        g.setGroupName(req.getGroupName());
        UserGroup saved = groupRepo.save(g);
        return ResponseEntity.ok(new GroupDTO(saved.getId(), saved.getGroupName()));
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> list = groupRepo.findAll()
                .stream()
                .map(g -> new GroupDTO(g.getId(), g.getGroupName()))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{groupId}/teachers")
    public ResponseEntity<List<UserDTO>> getTeachersInGroup(@PathVariable Integer groupId) {
        List<Integer> userIds = groupMemberRepo
                .findAllByUserGroup_Id(groupId)
                .stream()
                .map(gm -> gm.getUser().getId())
                .toList();

        List<UserDTO> teachers = userRepo.findAllById(userIds)
                .stream()
                .filter(u -> u.getRole() == User.Role.T)
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getRole()))
                .toList();

        return ResponseEntity.ok(teachers);
    }
}
