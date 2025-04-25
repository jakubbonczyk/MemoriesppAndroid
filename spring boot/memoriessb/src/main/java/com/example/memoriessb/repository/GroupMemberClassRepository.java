package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMemberClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberClassRepository
        extends JpaRepository<GroupMemberClass, Integer> {
    List<GroupMemberClass> findAllByGroupMember_Id(Integer groupMemberId);
    List<GroupMemberClass> findAllByGroupMember_UserGroup_Id(int groupId);
    List<GroupMemberClass> findByGroupMember_UserGroup_Id(int groupId);
}



