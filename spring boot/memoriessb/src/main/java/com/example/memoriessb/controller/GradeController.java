package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.GradeRequest;
import com.example.memoriessb.DTO.GradeResponse;
import com.example.memoriessb.etities.Grade;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.etities.User;
import com.example.memoriessb.repository.GradeRepository;
import com.example.memoriessb.repository.SchoolClassRepository;
import com.example.memoriessb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    @PostMapping
    public ResponseEntity<String> addGrade(@RequestBody GradeRequest request) {
        try {
            User student = userRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));

            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

            SchoolClass schoolClass = schoolClassRepository.findById(request.getClassId())
                    .orElseThrow(() -> new IllegalArgumentException("Class not found"));

            Grade grade = new Grade();
            grade.setGrade(request.getGrade());
            grade.setDescription(request.getDescription());
            grade.setStudent(student);
            grade.setTeacher(teacher);
            grade.setSchoolClass(schoolClass);

            gradeRepository.save(grade);

            return ResponseEntity.ok("Grade saved");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd zapisu: " + e.getMessage());
        }
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

}
