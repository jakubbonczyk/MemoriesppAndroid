package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMemberClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji {@link GroupMemberClass}.
 * Umożliwia dostęp do danych o przypisaniach członków grup do klas (przedmiotów).
 */
public interface GroupMemberClassRepository extends JpaRepository<GroupMemberClass, Integer> {

    /**
     * Zwraca wszystkie przypisania dla danego członka grupy.
     *
     * @param groupMemberId identyfikator członka grupy
     * @return lista przypisań do klas
     */
    List<GroupMemberClass> findAllByGroupMember_Id(Integer groupMemberId);

    /**
     * Zwraca wszystkie przypisania dla danej grupy użytkowników.
     *
     * @param groupId identyfikator grupy
     * @return lista przypisań do klas
     */
    List<GroupMemberClass> findByGroupMember_UserGroup_Id(int groupId);

    /**
     * Zwraca pierwsze przypisanie dla konkretnego użytkownika w danej grupie.
     *
     * @param groupId identyfikator grupy
     * @param userId  identyfikator użytkownika
     * @return przypisanie, jeśli istnieje
     */
    Optional<GroupMemberClass> findFirstByGroupMember_UserGroup_IdAndGroupMember_User_Id(Integer groupId, Integer userId);

    /**
     * Zwraca wszystkie przypisania powiązane z danym członkiem grupy.
     *
     * @param groupMemberId identyfikator członka grupy
     * @return lista przypisań do klas
     */
    List<GroupMemberClass> findByGroupMember_Id(Integer groupMemberId);

    /**
     * Zwraca wszystkie przypisania dotyczące konkretnej klasy (przedmiotu).
     *
     * @param classId identyfikator klasy
     * @return lista przypisań członków grup do tej klasy
     */
    List<GroupMemberClass> findBySchoolClass_Id(Integer classId);
}
