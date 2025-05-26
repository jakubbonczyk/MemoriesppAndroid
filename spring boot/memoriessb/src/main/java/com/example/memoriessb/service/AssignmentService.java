package com.example.memoriessb.service;

import com.example.memoriessb.etities.*;
import com.example.memoriessb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis odpowiedzialny za przypisywanie nauczycieli do grup i przedmiotów.
 * Umożliwia dodawanie nauczyciela do grupy oraz przypisywanie go do wybranych klas.
 */
@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;
    private final UserGroupRepository userGroupRepo;
    private final SchoolClassRepository classRepo;
    private final GroupMemberClassRepository gmClassRepo;

    /**
     * Przypisuje nauczyciela do wybranej grupy.
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy, do której ma zostać przypisany
     * @throws IllegalArgumentException jeśli użytkownik lub grupa nie istnieją
     */
    public void assignTeacherToGroup(int userId, int groupId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiego usera: " + userId));
        UserGroup group = userGroupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiej grupy: " + groupId));

        GroupMember gm = new GroupMember();
        gm.setUser(user);
        gm.setUserGroup(group);
        groupMemberRepo.save(gm);
    }

    /**
     * Zwraca listę klas (przedmiotów), do których przypisany jest nauczyciel w danej grupie.
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @return lista przypisanych klas
     * @throws IllegalArgumentException jeśli nauczyciel nie należy do podanej grupy
     */
    public List<SchoolClass> getAssignedClasses(int userId, int groupId) {
        GroupMember gm = groupMemberRepo
                .findAllByUserGroup_Id(groupId).stream()
                .filter(m -> m.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik nie należy do tej grupy"));

        return gmClassRepo
                .findAllByGroupMember_Id(gm.getId())
                .stream()
                .map(GroupMemberClass::getSchoolClass)
                .toList();
    }

    /**
     * Przypisuje nauczyciela z danej grupy do wybranego przedmiotu (klasy).
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy, do której należy nauczyciel
     * @param classId identyfikator klasy (przedmiotu)
     * @throws IllegalArgumentException jeśli użytkownik nie należy do grupy lub klasa nie istnieje
     */
    public void assignTeacherToClass(int userId, int groupId, int classId) {
        GroupMember gm = groupMemberRepo
                .findAllByUserGroup_Id(groupId).stream()
                .filter(m -> m.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Użytkownik nie należy do tej grupy"));

        SchoolClass cls = classRepo.findById(classId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Brak przedmiotu o ID=" + classId));

        gmClassRepo.save(new GroupMemberClass(gm, cls));
    }
}
