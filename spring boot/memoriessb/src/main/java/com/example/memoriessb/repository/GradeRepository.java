package com.example.memoriessb.repository;

import com.example.memoriessb.etities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudent_IdAndSchoolClass_IdOrderByIdDesc(Integer studentId, Integer classId);
    List<Grade> findByStudent_IdAndNotifiedFalse(Integer studentId);
}
