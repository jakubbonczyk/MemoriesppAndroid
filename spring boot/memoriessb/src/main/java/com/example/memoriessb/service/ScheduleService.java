package com.example.memoriessb.service;// com/example/memoriessb/service/impl/ScheduleServiceImpl.java



import com.example.memoriessb.DTO.ScheduleRequestDTO;
import com.example.memoriessb.DTO.ScheduleResponseDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.etities.Schedule;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import com.example.memoriessb.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService{
    private final ScheduleRepository scheduleRepo;
    private final GroupMemberClassRepository assignmentRepo;


    public ScheduleResponseDTO createLesson(ScheduleRequestDTO dto) {
        GroupMemberClass gmc = assignmentRepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
        Schedule s = new Schedule();
        s.setGroupMemberClass(gmc);
        s.setLessonDate(dto.getLessonDate());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        Schedule saved = scheduleRepo.save(s);
        return new ScheduleResponseDTO(
                saved.getId(),
                gmc.getId(),
                saved.getLessonDate(),
                saved.getStartTime(),
                saved.getEndTime(),
                gmc.getGroupMember().getUser().getName() + " " + gmc.getGroupMember().getUser().getSurname(),
                gmc.getSchoolClass().getClassName()
        );
    }

    public List<ScheduleResponseDTO> getScheduleForGroup(int groupId, java.time.LocalDate from, java.time.LocalDate to) {
        return scheduleRepo.findByGroupAndDateRange(groupId, from, to);
    }
}
