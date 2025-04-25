package com.example.memoriessb.repository;

import com.example.memoriessb.DTO.ScheduleResponseDTO;
import com.example.memoriessb.etities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("""
      SELECT new com.example.memoriessb.DTO.ScheduleResponseDTO(
        s.id,
        gmc.id,
        s.lessonDate,
        s.startTime,
        s.endTime,
        concat(u.name, ' ', u.surname),
        c.className
      )
      FROM Schedule s
      JOIN s.groupMemberClass gmc
      JOIN gmc.groupMember gm
      JOIN gm.user u
      JOIN gmc.schoolClass c
      WHERE gm.userGroup.id = :groupId
        AND s.lessonDate BETWEEN :from AND :to
      ORDER BY s.lessonDate, s.startTime
    """)
    List<ScheduleResponseDTO> findByGroupAndDateRange(
            @Param("groupId") int groupId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}