package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMemberClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberClassRepository
        extends JpaRepository<GroupMemberClass, Integer> {
    List<GroupMemberClass> findAllByGroupMember_Id(Integer groupMemberId);
    List<GroupMemberClass> findByGroupMember_UserGroup_Id(int groupId);
    Optional<GroupMemberClass> findFirstByGroupMember_UserGroup_IdAndGroupMember_User_Id(Integer groupId, Integer userId);
    List<GroupMemberClass> findByGroupMember_Id(Integer groupMemberId);
}



