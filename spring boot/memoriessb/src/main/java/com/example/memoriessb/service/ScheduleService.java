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
public class ScheduleService{
    private final ScheduleRepository scheduleRepo;
    private final GroupMemberClassRepository assignmentRepo;


    public ScheduleResponseDTO createLesson(ScheduleRequestDTO dto) {
        GroupMemberClass gmc = assignmentRepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        Schedule firstLesson = new Schedule();
        firstLesson.setGroupMemberClass(gmc);
        firstLesson.setLessonDate(dto.getLessonDate());
        firstLesson.setStartTime(dto.getStartTime());
        firstLesson.setEndTime(dto.getEndTime());
        firstLesson.setGenerated(false);

        Schedule savedFirst = scheduleRepo.save(firstLesson);

        // Dodaj kolejne lekcje co tydzień w tym samym miesiącu
        for (int i = 1; i <= 4; i++) {
            LocalDate nextDate = dto.getLessonDate().plusWeeks(i);
            if (nextDate.getMonth() == dto.getLessonDate().getMonth()) {
                Schedule recurring = new Schedule();
                recurring.setGroupMemberClass(gmc);
                recurring.setLessonDate(nextDate);
                recurring.setStartTime(dto.getStartTime());
                recurring.setEndTime(dto.getEndTime());
                recurring.setGenerated(true); // żeby było jasne, że to wygenerowane

                scheduleRepo.save(recurring);
            }
        }

        // Zwracamy tylko pierwszy wpis
        return new ScheduleResponseDTO(
                savedFirst.getId(),
                gmc.getId(),
                savedFirst.getLessonDate(),
                savedFirst.getStartTime(),
                savedFirst.getEndTime(),
                gmc.getGroupMember().getUser().getName() + " " + gmc.getGroupMember().getUser().getSurname(),
                gmc.getSchoolClass().getClassName()
        );
    }


    public List<ScheduleResponseDTO> getScheduleForGroup(int groupId, java.time.LocalDate from, java.time.LocalDate to) {
        return scheduleRepo.findByGroupAndDateRange(groupId, from, to);
    }
}
