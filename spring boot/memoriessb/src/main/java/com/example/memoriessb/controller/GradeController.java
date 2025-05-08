package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.*;
import com.example.memoriessb.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                gradeService.getGradesForSubject(studentId, 0)
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

}
