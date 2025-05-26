package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.*;
import com.example.memoriessb.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

/**
 * Kontroler REST odpowiedzialny za zarządzanie ocenami uczniów.
 * Udostępnia endpointy do dodawania ocen, pobierania ich list, szczegółów oraz nowych ocen.
 */
@Slf4j
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    /**
     * Dodaje nową ocenę dla ucznia.
     *
     * @param req dane oceny (uczeń, nauczyciel, przedmiot, typ, opis)
     * @return odpowiedź HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Void> addGrade(@RequestBody GradeRequest req) {
        gradeService.addGrade(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Zwraca wszystkie oceny danego ucznia, posortowane malejąco po ID.
     *
     * @param studentId identyfikator ucznia
     * @return lista ocen w formie podsumowań {@link GradeSummaryDTO}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeSummaryDTO>> getGradesForStudent(
            @PathVariable Integer studentId) {
        return ResponseEntity.ok(
                gradeService.getAllGradesForStudent(studentId)
        );
    }

    /**
     * Zwraca oceny ucznia z konkretnego przedmiotu.
     *
     * @param studentId identyfikator ucznia
     * @param subjectId identyfikator przedmiotu
     * @return lista ocen {@link GradeSummaryDTO}
     */
    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<GradeSummaryDTO>> getGradesForSubject(
            @PathVariable int studentId,
            @PathVariable int subjectId
    ) {
        return ResponseEntity.ok(
                gradeService.getGradesForSubject(studentId, subjectId)
        );
    }

    /**
     * Zwraca szczegółowe informacje o pojedynczej ocenie.
     *
     * @param gradeId identyfikator oceny
     * @return szczegóły oceny {@link GradeDetailDTO}
     */
    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeDetailDTO> getGradeDetails(
            @PathVariable int gradeId
    ) {
        return ResponseEntity.ok(
                gradeService.getGradeDetails(gradeId)
        );
    }

    /**
     * Zwraca listę nowych (nieprzeczytanych) ocen ucznia i oznacza je jako przeczytane.
     *
     * @param studentId identyfikator ucznia
     * @return lista nowych ocen {@link NewGradeDTO}
     */
    @GetMapping("/student/{studentId}/new")
    public ResponseEntity<List<NewGradeDTO>> getNewGrades(
            @PathVariable Integer studentId) {
        List<NewGradeDTO> dtos = gradeService.getNewGradesForStudent(studentId);
        return ResponseEntity.ok(dtos);
    }

    /**
     * Zwraca listę przedmiotów, w których nauczyciel wystawił oceny, wraz ze średnią.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista klas {@link SchoolClassDTO}
     */
    @GetMapping("/teacher/{teacherId}/classes")
    public ResponseEntity<List<SchoolClassDTO>> getClassesForTeacher(
            @PathVariable int teacherId) {
        return ResponseEntity.ok(
                gradeService.getClassesForTeacher(teacherId)
        );
    }

    /**
     * Zwraca listę grup, do których należą uczniowie oceniani przez nauczyciela.
     *
     * @param teacherId identyfikator nauczyciela
     * @return lista grup {@link TeacherGroupDTO}
     */
    @GetMapping("/teacher/{teacherId}/groups")
    public ResponseEntity<List<TeacherGroupDTO>> getGroupsForTeacher(
            @PathVariable int teacherId) {
        return ResponseEntity.ok(
                gradeService.getGroupsForTeacher(teacherId)
        );
    }

    /**
     * Zwraca listę uczniów przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return lista uczniów {@link StudentDTO}
     */
    @GetMapping("/group/{groupId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsForGroup(
            @PathVariable int groupId) {
        return ResponseEntity.ok(
                gradeService.getStudentsForGroup(groupId)
        );
    }

}
