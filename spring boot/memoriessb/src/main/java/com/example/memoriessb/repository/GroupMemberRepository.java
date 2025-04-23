package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    List<GroupMember> findAllByUserGroup_Id(Integer userGroupId);

    List<GroupMember> findAllByUser_Id(Integer userId);
}
