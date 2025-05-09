package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.*;
import com.example.memoriessb.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<Void> addGrade(@RequestBody GradeRequest req) {
        gradeService.addGrade(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeSummaryDTO>> getGradesForStudent(
            @PathVariable Integer studentId) {
        return ResponseEntity.ok(
                gradeService.getAllGradesForStudent(studentId)
        );
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<GradeSummaryDTO>> getGradesForSubject(
            @PathVariable int studentId,
            @PathVariable int subjectId
    ) {
        return ResponseEntity.ok(
                gradeService.getGradesForSubject(studentId, subjectId)
        );
    }

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDetailDTO> getGradeDetails(
            @PathVariable int gradeId
    ) {
        return ResponseEntity.ok(
                gradeService.getGradeDetails(gradeId)
        );
    }

    @GetMapping("/student/{studentId}/new")
    public ResponseEntity<List<NewGradeDTO>> getNewGrades(
            @PathVariable Integer studentId) {
        List<NewGradeDTO> dtos = gradeService.getNewGradesForStudent(studentId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/teacher/{teacherId}/classes")
    public ResponseEntity<List<SchoolClassDTO>> getClassesForTeacher(
            @PathVariable int teacherId) {
        return ResponseEntity.ok(
                gradeService.getClassesForTeacher(teacherId)
        );
    }


    // 1) lista grup nauczyciela
    @GetMapping("/teacher/{teacherId}/groups")
    public ResponseEntity<List<TeacherGroupDTO>> getGroupsForTeacher(
            @PathVariable int teacherId) {
        return ResponseEntity.ok(
                gradeService.getGroupsForTeacher(teacherId)
        );
    }

    // 2) uczniowie w grupie
    @GetMapping("/group/{groupId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsForGroup(
            @PathVariable int groupId) {
        return ResponseEntity.ok(
                gradeService.getStudentsForGroup(groupId)
        );
    }

}
