package com.example.memoriessb.controller;


import com.example.memoriessb.DTO.AssignmentDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST odpowiedzialny za zarządzanie przypisaniami nauczycieli do przedmiotów w ramach grup.
 * Umożliwia pobranie listy przypisań w postaci uproszczonych danych.
 */
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupMemberClassController {

    private final GroupMemberClassRepository repo;

    /**
     * Zwraca listę przypisań nauczycieli do przedmiotów w danej grupie.
     *
     * @param groupId identyfikator grupy
     * @return lista przypisań w postaci {@link AssignmentDTO}
     */
    @GetMapping("/{groupId}/assignments")
    public List<AssignmentDTO> getAssignments(@PathVariable int groupId) {
        return repo.findByGroupMember_UserGroup_Id(groupId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Konwertuje obiekt {@link GroupMemberClass} do {@link AssignmentDTO}.
     *
     * @param gmc przypisanie nauczyciel–przedmiot
     * @return DTO z podstawowymi informacjami
     */
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
