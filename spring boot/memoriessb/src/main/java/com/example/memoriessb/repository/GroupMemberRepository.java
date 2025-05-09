package com.example.memoriessb.repository;

import com.example.memoriessb.etities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    // pobiera WSZYSTKIE rekordy GroupMember dla danej grupy
    List<GroupMember> findAllByUserGroup_Id(Integer userGroupId);

    // już miałeś:
    List<GroupMember> findAllByUser_Id(Integer userId);
    Optional<GroupMember> findByUser_Id(Integer userId);
    List<GroupMember> findByUserGroup_Id(Integer userGroupId);

}
