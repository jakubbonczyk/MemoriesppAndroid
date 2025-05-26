package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.ClassDTO;
import com.example.memoriessb.DTO.CreateGroupRequest;
import com.example.memoriessb.DTO.GroupDTO;
import com.example.memoriessb.DTO.UserDTO;
import com.example.memoriessb.etities.UserGroup;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.repository.UserGroupRepository;
import com.example.memoriessb.repository.UserRepository;
import com.example.memoriessb.service.GroupMemberClassService;
import com.example.memoriessb.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST odpowiedzialny za zarządzanie grupami użytkowników.
 * Umożliwia tworzenie grup, pobieranie ich listy oraz członków (nauczycieli i uczniów).
 */
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;
    private final UserGroupRepository groupRepo;
    private final UserGroupService userGroupService;
    private final GroupMemberClassService groupMemberClassService;

    /**
     * Zwraca listę uczniów przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return lista uczniów w postaci {@link UserDTO}
     */
    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<UserDTO>> getStudentsInGroup(@PathVariable int groupId) {
        List<Integer> userIds = groupMemberRepo
                .findAllByUserGroup_Id(groupId)
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

    /**
     * Tworzy nową grupę użytkowników.
     *
     * @param req dane nowej grupy (nazwa)
     * @return utworzona grupa w postaci {@link GroupDTO}
     */
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupRequest req) {
        UserGroup g = new UserGroup();
        g.setGroupName(req.getGroupName());
        UserGroup saved = groupRepo.save(g);
        return ResponseEntity.ok(new GroupDTO(saved.getId(), saved.getGroupName()));
    }

    /**
     * Zwraca listę wszystkich grup w systemie.
     *
     * @return lista grup w postaci {@link GroupDTO}
     */
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> list = groupRepo.findAll()
                .stream()
                .map(g -> new GroupDTO(g.getId(), g.getGroupName()))
                .toList();
        return ResponseEntity.ok(list);
    }

    /**
     * Zwraca listę nauczycieli przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return lista nauczycieli w postaci {@link UserDTO}
     */
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

    /**
     * Zwraca listę grup, do których przypisany jest nauczyciel.
     *
     * @param id identyfikator nauczyciela
     * @return lista grup w postaci {@link GroupDTO}
     */
    @GetMapping("/teacher/{id}")
    public List<GroupDTO> getGroupsForTeacher(@PathVariable Integer id) {
        return userGroupService.findGroupsForTeacher(id);
    }

    /**
     * Zwraca przedmiot, do którego przypisany jest nauczyciel w danej grupie.
     *
     * @param groupId   identyfikator grupy
     * @param teacherId identyfikator nauczyciela
     * @return przedmiot w postaci {@link ClassDTO}
     */
    @GetMapping("/{groupId}/teachers/{teacherId}/subject")
    public ClassDTO getSubjectForGroupAndTeacher(
            @PathVariable Integer groupId,
            @PathVariable Integer teacherId
    ) {
        return groupMemberClassService.findSubjectByGroupAndTeacher(groupId, teacherId);
    }

}
