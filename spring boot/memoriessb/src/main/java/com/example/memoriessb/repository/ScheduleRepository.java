package com.example.memoriessb.repository;

import com.example.memoriessb.DTO.ScheduleResponseDTO;
import com.example.memoriessb.etities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repozytorium JPA dla encji {@link Schedule}.
 * Odpowiada za operacje na planie zajęć oraz projekcje danych do {@link ScheduleResponseDTO}.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    /**
     * Zwraca listę zaplanowanych lekcji dla danej grupy w określonym przedziale dat.
     *
     * @param groupId identyfikator grupy użytkowników
     * @param from    data początkowa zakresu
     * @param to      data końcowa zakresu
     * @return lista lekcji w postaci DTO
     */
    @Query("""
      SELECT new com.example.memoriessb.DTO.ScheduleResponseDTO(
        s.id,
        gmc.id,
        s.lessonDate,
        s.startTime,
        s.endTime,
        concat(u.name,' ',u.surname),
        c.className,
        gm.userGroup.groupName
      )
      FROM Schedule s
      JOIN s.groupMemberClass gmc
      JOIN gmc.groupMember gm
      JOIN gm.user u
      JOIN gmc.schoolClass c
      WHERE gm.userGroup.id   = :groupId
        AND s.lessonDate BETWEEN :from AND :to
      ORDER BY s.lessonDate, s.startTime
    """)
    List<ScheduleResponseDTO> findByGroupAndDateRange(
            @Param("groupId") int groupId,
            @Param("from")    LocalDate from,
            @Param("to")      LocalDate to
    );

    /**
     * Zwraca listę zaplanowanych lekcji prowadzonych przez danego nauczyciela
     * w określonym przedziale dat.
     *
     * @param teacherId identyfikator nauczyciela
     * @param from      data początkowa zakresu
     * @param to        data końcowa zakresu
     * @return lista lekcji w postaci DTO
     */
    @Query("""
      SELECT new com.example.memoriessb.DTO.ScheduleResponseDTO(
        s.id,
        gmc.id,
        s.lessonDate,
        s.startTime,
        s.endTime,
        concat(u.name,' ',u.surname),
        c.className,
        gm.userGroup.groupName
      )
      FROM Schedule s
      JOIN s.groupMemberClass gmc
      JOIN gmc.groupMember gm
      JOIN gm.user u
      JOIN gmc.schoolClass c
      WHERE u.id               = :teacherId
        AND s.lessonDate BETWEEN :from AND :to
      ORDER BY s.lessonDate, s.startTime
    """)
    List<ScheduleResponseDTO> findByTeacherAndDateRange(
            @Param("teacherId") int teacherId,
            @Param("from")      LocalDate from,
            @Param("to")        LocalDate to
    );
}
