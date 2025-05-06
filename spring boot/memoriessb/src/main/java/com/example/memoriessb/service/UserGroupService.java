package com.example.memoriessb.service;

import com.example.memoriessb.DTO.GroupDTO;
import com.example.memoriessb.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGroupService {
    private final UserGroupRepository groupRepo;

    public UserGroupService(UserGroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public List<GroupDTO> findGroupsForTeacher(Integer teacherId) {
        return groupRepo.findDistinctByMembers_User_Id(teacherId)
                .stream()
                .map(g -> new GroupDTO(g.getId(), g.getGroupName()))
                .collect(Collectors.toList());
    }
}
