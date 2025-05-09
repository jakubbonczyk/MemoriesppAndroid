package com.example.memoriessb.repository;

import com.example.memoriessb.etities.Grade;
import com.example.memoriessb.etities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudent_IdAndSchoolClass_IdOrderByIdDesc(Integer studentId, Integer classId);
    List<Grade> findByStudent_IdAndNotifiedFalse(Integer studentId);

    @Query("SELECT DISTINCT g.schoolClass FROM Grade g WHERE g.teacher.id = :teacherId")
    List<SchoolClass> findDistinctClassesByTeacherId(@Param("teacherId") Integer teacherId);
    List<Grade> findByTeacher_IdAndSchoolClass_Id(Integer teacherId, Integer classId);

    @Query("SELECT DISTINCT gm.userGroup.id " +
            "FROM Grade g JOIN g.student s " +
            "JOIN GroupMember gm ON gm.user.id = s.id " +
            "WHERE g.teacher.id = :teacherId")
    List<Integer> findDistinctGroupIdsByTeacherId(@Param("teacherId") Integer teacherId);

    List<Grade> findByStudent_IdOrderByIdDesc(Integer studentId);

}
