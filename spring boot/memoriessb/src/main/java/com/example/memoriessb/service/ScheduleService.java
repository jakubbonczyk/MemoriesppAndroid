package com.example.memoriessb.service;

import com.example.memoriessb.DTO.ScheduleRequestDTO;
import com.example.memoriessb.DTO.ScheduleResponseDTO;
import com.example.memoriessb.etities.GroupMemberClass;
import com.example.memoriessb.etities.Schedule;
import com.example.memoriessb.repository.GroupMemberClassRepository;
import com.example.memoriessb.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepo;
    private final GroupMemberClassRepository assignmentRepo;

    public ScheduleResponseDTO createLesson(ScheduleRequestDTO dto) {
        GroupMemberClass gmc = assignmentRepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
        Schedule first = new Schedule();
        first.setGroupMemberClass(gmc);
        first.setLessonDate(dto.getLessonDate());
        first.setStartTime(dto.getStartTime());
        first.setEndTime(dto.getEndTime());
        first.setGenerated(false);
        Schedule saved = scheduleRepo.save(first);
        for (int i = 1; i <= 4; i++) {
            LocalDate next = dto.getLessonDate().plusWeeks(i);
            if (next.getMonth() == dto.getLessonDate().getMonth()) {
                Schedule r = new Schedule();
                r.setGroupMemberClass(gmc);
                r.setLessonDate(next);
                r.setStartTime(dto.getStartTime());
                r.setEndTime(dto.getEndTime());
                r.setGenerated(true);
                scheduleRepo.save(r);
            }
        }
        return new ScheduleResponseDTO(
                saved.getId(),
                gmc.getId(),
                saved.getLessonDate(),
                saved.getStartTime(),
                saved.getEndTime(),
                gmc.getGroupMember().getUser().getName() + " " + gmc.getGroupMember().getUser().getSurname(),
                gmc.getSchoolClass().getClassName(),
                gmc.getGroupMember().getUserGroup().getGroupName()
        );
    }

    public List<ScheduleResponseDTO> getScheduleForGroup(int groupId, LocalDate from, LocalDate to) {
        return scheduleRepo.findByGroupAndDateRange(groupId, from, to);
    }

    public List<ScheduleResponseDTO> getScheduleForTeacher(int teacherId, LocalDate from, LocalDate to) {
        return scheduleRepo.findByTeacherAndDateRange(teacherId, from, to);
    }
}
