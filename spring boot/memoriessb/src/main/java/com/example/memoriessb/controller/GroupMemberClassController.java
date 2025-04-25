package com.example.memoriessb.controller;


import com.example.memoriessb.DTO.AssignmentDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupMemberClassController {
    private final GroupMemberClassRepository repo;

    @GetMapping("/{groupId}/assignments")
    public List<AssignmentDTO> getAssignments(@PathVariable int groupId) {
        return repo.findByGroupMember_UserGroup_Id(groupId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private AssignmentDTO toDto(GroupMemberClass gmc) {
        var u = gmc.getGroupMember().getUser();
        var c = gmc.getSchoolClass();
        return new AssignmentDTO(
                gmc.getId(),
                u.getName() + " " + u.getSurname(),
                c.getId(),
                c.getClassName()
        );
    }
}
