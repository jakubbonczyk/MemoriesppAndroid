package com.example.memoriessb.repository;

import com.example.memoriessb.etities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repozytorium JPA dla encji {@link UserGroup}.
 * Umożliwia operacje na grupach użytkowników i wyszukiwanie grup po członkach.
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    /**
     * Zwraca listę unikalnych grup, do których należy dany użytkownik.
     *
     * @param userId identyfikator użytkownika
     * @return lista grup użytkownika
     */
    List<UserGroup> findDistinctByMembers_User_Id(Integer userId);
}
