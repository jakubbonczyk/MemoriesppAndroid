package com.example.memoriessb.controller;


import com.example.memoriessb.DTO.SchoolClassDTO;
import com.example.memoriessb.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/class")
@AllArgsConstructor
public class ClassController {

    private final GradeService gradeService;

    @GetMapping("/student/{userId}/subjects")
    public ResponseEntity<List<SchoolClassDTO>> getSubjectsForStudent(@PathVariable int userId) {
        List<SchoolClassDTO> subjects = gradeService.getSubjectsForStudent(userId);
        return ResponseEntity.ok(subjects);
    }
}
