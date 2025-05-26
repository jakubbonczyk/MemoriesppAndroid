package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium JPA dla encji {@link GroupMember}.
 * Umożliwia operacje na przypisaniach użytkowników do grup.
 */
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    /**
     * Zwraca wszystkich członków danej grupy użytkowników.
     *
     * @param userGroupId identyfikator grupy
     * @return lista członków grupy
     */
    List<GroupMember> findAllByUserGroup_Id(Integer userGroupId);

    /**
     * Zwraca wszystkie przypisania grupowe dla danego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista przypisań do grup
     */
    List<GroupMember> findAllByUser_Id(Integer userId);

    /**
     * Zwraca przypisanie użytkownika do grupy (jeśli istnieje).
     *
     * @param userId identyfikator użytkownika
     * @return przypisanie użytkownika do grupy
     */
    Optional<GroupMember> findByUser_Id(Integer userId);

    /**
     * Zwraca wszystkich członków danej grupy.
     *
     * @param userGroupId identyfikator grupy
     * @return lista przypisań
     */
    List<GroupMember> findByUserGroup_Id(Integer userGroupId);
}
