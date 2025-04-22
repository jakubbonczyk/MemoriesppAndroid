package com.example.memoriessb.repository;

import com.example.memoriessb.etities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT gm.user FROM GroupMember gm WHERE gm.group.id = :groupId AND gm.user.role = 'S'")
    List<User> findStudentsByGroupId(@Param("groupId") Integer groupId);

}
