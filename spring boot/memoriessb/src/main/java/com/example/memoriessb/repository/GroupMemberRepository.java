package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    // zamiast findAllByGroup_Id użyj nazwy pola userGroup
    List<GroupMember> findAllByUserGroup_Id(Integer userGroupId);
}
