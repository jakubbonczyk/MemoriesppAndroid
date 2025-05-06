package com.example.memoriessb.repository;

import com.example.memoriessb.DTO.GradeDetailDTO;
import com.example.memoriessb.DTO.GradeSummaryDTO;
import com.example.memoriessb.etities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudentId(Integer studentId);

    @Query("SELECT new com.example.memoriessb.DTO.GradeSummaryDTO(g.id, g.type, g.grade) " +
            "FROM Grade g WHERE g.student.id = :studentId AND g.schoolClass.id = :classId")
    List<GradeSummaryDTO> findGradesForStudentAndSubject(@Param("studentId") int studentId,
                                                         @Param("classId") int classId);

    @Query("SELECT new com.example.memoriessb.DTO.GradeDetailDTO(g.id, g.type, g.grade, g.description, t.name, t.surname) " +
            "FROM Grade g JOIN g.teacher t WHERE g.id = :gradeId")
    Optional<GradeDetailDTO> getGradeDetails(@Param("gradeId") int gradeId);
}
