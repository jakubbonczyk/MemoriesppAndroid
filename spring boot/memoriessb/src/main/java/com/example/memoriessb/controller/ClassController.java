package com.example.memoriessb.controller;


import com.example.memoriessb.DTO.SchoolClassDTO;
import com.example.memoriessb.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Kontroler REST odpowiedzialny za operacje na przedmiotach (klasach).
 * Umożliwia pobieranie listy przedmiotów przypisanych do ucznia.
 */
@RestController
@RequestMapping("/api/class")
@AllArgsConstructor
public class ClassController {

    private final GradeService gradeService;

    /**
     * Zwraca listę przedmiotów, do których przypisany jest dany uczeń.
     * Każdy przedmiot zawiera obliczoną średnią ocen z tego przedmiotu.
     *
     * @param userId identyfikator ucznia
     * @return lista przedmiotów w postaci {@link SchoolClassDTO}
     */
    @GetMapping("/student/{userId}/subjects")
    public ResponseEntity<List<SchoolClassDTO>> getSubjectsForStudent(@PathVariable int userId) {
        List<SchoolClassDTO> subjects = gradeService.getSubjectsForStudent(userId);
        return ResponseEntity.ok(subjects);
    }
}
