package com.example.memoriessb.controller;

import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assign")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<String> assignUserToGroup(
            @PathVariable int userId,
            @PathVariable int groupId) {

        assignmentService.assignTeacherToGroup(userId, groupId);
        return ResponseEntity.ok("OK");
    }
}
