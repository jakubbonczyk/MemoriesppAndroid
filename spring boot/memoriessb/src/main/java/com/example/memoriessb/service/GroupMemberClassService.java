package com.example.memoriessb.service;

import com.example.memoriessb.DTO.ClassDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serwis odpowiedzialny za pobieranie informacji o przypisaniach nauczycieli do przedmiotów w grupach.
 */
@RequiredArgsConstructor
@Service
public class GroupMemberClassService {

    private final GroupMemberClassRepository repo;

    /**
     * Zwraca przedmiot (klasę), do którego przypisany jest nauczyciel w danej grupie.
     *
     * @param groupId    identyfikator grupy użytkowników
     * @param teacherId  identyfikator nauczyciela
     * @return obiekt {@link ClassDTO} z danymi przedmiotu
     * @throws EntityNotFoundException jeśli brak przypisania nauczyciela do klasy w tej grupie
     */
    public ClassDTO findSubjectByGroupAndTeacher(Integer groupId, Integer teacherId) {
        GroupMemberClass gmc = repo
                .findFirstByGroupMember_UserGroup_IdAndGroupMember_User_Id(groupId, teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Brak przypisania klasy/przedmiotu"));
        SchoolClass c = gmc.getSchoolClass();
        return new ClassDTO(c.getId(), c.getClassName());
    }
}
