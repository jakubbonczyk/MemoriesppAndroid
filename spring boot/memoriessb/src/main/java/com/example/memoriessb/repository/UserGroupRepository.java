package com.example.memoriessb.repository;

import com.example.memoriessb.etities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    List<UserGroup> findDistinctByMembers_User_Id(Integer userId);
}
