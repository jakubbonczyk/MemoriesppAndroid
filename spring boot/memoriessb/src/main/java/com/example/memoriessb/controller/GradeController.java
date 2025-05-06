package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.GradeDetailDTO;
import com.example.memoriessb.DTO.GradeRequest;
import com.example.memoriessb.DTO.GradeResponse;
import com.example.memoriessb.DTO.GradeSummaryDTO;
import com.example.memoriessb.etities.Grade;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GradeRepository;
import com.example.memoriessb.repository.SchoolClassRepository;
import com.example.memoriessb.repository.UserRepository;
import com.example.memoriessb.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeRepository gradeRepository;
    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<Void> addGrade(@RequestBody GradeRequest req) {
        gradeService.addGrade(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponse>> getGradesForStudent(@PathVariable Integer studentId) {
        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        List<GradeResponse> response = grades.stream()
                .map(grade -> new GradeResponse(
                        grade.getId(),
                        grade.getGrade(),
                        grade.getDescription(),
                        grade.getTeacher().getName() + " " + grade.getTeacher().getSurname()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<GradeSummaryDTO>> getGradesForSubject(
            @PathVariable int studentId, @PathVariable int subjectId) {
        return ResponseEntity.ok(gradeService.getGradesForSubject(studentId, subjectId));
    }

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDetailDTO> getGradeDetails(@PathVariable int gradeId) {
        return ResponseEntity.ok(gradeService.getGradeDetails(gradeId));
    }


}
