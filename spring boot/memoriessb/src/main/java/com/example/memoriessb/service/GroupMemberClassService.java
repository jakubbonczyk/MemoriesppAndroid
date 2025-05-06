package com.example.memoriessb.service;

import com.example.memoriessb.DTO.ClassDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GroupMemberClassService {

    private final GroupMemberClassRepository repo;

    public ClassDTO findSubjectByGroupAndTeacher(Integer groupId, Integer teacherId) {
        GroupMemberClass gmc = repo
                .findFirstByGroupMember_UserGroup_IdAndGroupMember_User_Id(groupId, teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Brak przypisania klasy/przedmiotu"));
        SchoolClass c = gmc.getSchoolClass();
        return new ClassDTO(c.getId(), c.getClassName());
    }
}
