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
    List<Grade> findByStudent_IdAndSchoolClass_IdOrderByIdDesc(Integer studentId, Integer classId);
}
