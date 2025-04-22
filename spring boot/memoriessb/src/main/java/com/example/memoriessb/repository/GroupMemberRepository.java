package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    /**
     * Zwraca wszystkich członków grupy o danym id (kolumna groups_idgroups).
     */
    List<GroupMember> findAllByGroup_Id(Integer groupId);
}
