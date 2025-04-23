package com.example.memoriessb.service;

import com.example.memoriessb.etities.GroupMember;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.etities.UserGroup;
import com.example.memoriessb.repository.GroupMemberRepository;
import com.example.memoriessb.repository.UserGroupRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final GroupMemberRepository groupMemberRepo;
    private final UserRepository userRepo;
    private final UserGroupRepository userGroupRepo;

    public void assignTeacherToGroup(int userId, int groupId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiego usera: " + userId));
        UserGroup group = userGroupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Nie ma takiej grupy: " + groupId));

        // Zapiszemy nowy GroupMember; JPA wygeneruje poprawny INSERT
        GroupMember gm = new GroupMember();
        gm.setUser(user);
        gm.setUserGroup(group);
        groupMemberRepo.save(gm);
    }
}
