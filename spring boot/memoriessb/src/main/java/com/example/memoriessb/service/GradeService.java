package com.example.memoriessb.service;


import com.example.memoriessb.DTO.SchoolClassDTO;
import com.example.memoriessb.etities.GroupMember;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import com.example.memoriessb.repository.GroupMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberClassRepository groupMemberClassRepository;

    public List<SchoolClassDTO> getSubjectsForStudent(int userId) {
        Optional<GroupMember> groupMember = groupMemberRepository.findByUser_Id(userId);

        if (groupMember.isEmpty()) {
            return List.of();
        }

        List<GroupMemberClass> gmcList = groupMemberClassRepository
                .findByGroupMemberId(groupMember.get().getId());

        return gmcList.stream()
                .map(gmc -> gmc.getSchoolClass())
                .distinct()
                .map(sc -> new SchoolClassDTO(sc.getId(), sc.getClassName()))
                .collect(Collectors.toList());
    }
}
