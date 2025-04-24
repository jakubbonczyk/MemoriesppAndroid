package com.example.memoriessb.service;

import com.example.memoriessb.etities.*;
import com.example.memoriessb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;
    private final UserGroupRepository userGroupRepo;
    private final SchoolClassRepository classRepo;
    private final GroupMemberClassRepository gmClassRepo;


    public void assignTeacherToGroup(int userId, int groupId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiego usera: " + userId));
        UserGroup group = userGroupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiej grupy: " + groupId));

        // Zapiszemy nowy GroupMember; JPA wygeneruje poprawny INSERT
        GroupMember gm = new GroupMember();
        gm.setUser(user);
        gm.setUserGroup(group);
        groupMemberRepo.save(gm);
    }

    public List<SchoolClass> getAssignedClasses(int userId, int groupId) {
        // 1) znajdź wpis w group_members
        GroupMember gm = groupMemberRepo
                .findAllByUserGroup_Id(groupId).stream()
                .filter(m -> m.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik nie należy do tej grupy"));

        // 2) pobierz wszystkie powiązania z group_members_has_class
        return gmClassRepo
                .findAllByGroupMember_Id(gm.getId())
                .stream()
                .map(GroupMemberClass::getSchoolClass)
                .toList();
    }

    public void assignTeacherToClass(int userId, int groupId, int classId) {
        // 1) Znajdź wpis w group_members
        GroupMember gm = groupMemberRepo
                .findAllByUserGroup_Id(groupId).stream()
                .filter(m -> m.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Użytkownik nie należy do tej grupy"));

        // 2) Znajdź encję SchoolClass
        SchoolClass cls = classRepo.findById(classId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Brak przedmiotu o ID=" + classId));

        // 3) Zapisz nową relację (INSERT do group_members_has_class)
        gmClassRepo.save(new GroupMemberClass(gm, cls));
    }
}
