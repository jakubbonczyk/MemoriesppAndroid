package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.ClassDTO;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST odpowiedzialny za przypisywanie nauczycieli do grup i przedmiotów.
 * Umożliwia przypisanie nauczyciela do grupy oraz do konkretnej klasy w tej grupie.
 */
@RestController
@RequestMapping("/api/assign")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Przypisuje nauczyciela do wskazanej grupy.
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @return odpowiedź HTTP 200 OK
     */
    @PostMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<String> assignUserToGroup(
            @PathVariable int userId,
            @PathVariable int groupId) {

        assignmentService.assignTeacherToGroup(userId, groupId);
        return ResponseEntity.ok("OK");
    }

    /**
     * Przypisuje nauczyciela do konkretnej klasy (przedmiotu) w ramach grupy.
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @param classId identyfikator klasy (przedmiotu)
     * @return odpowiedź HTTP 200 OK
     */
    @PostMapping("/user/{userId}/group/{groupId}/class/{classId}")
    public ResponseEntity<Void> assign(
            @PathVariable int userId,
            @PathVariable int groupId,
            @PathVariable int classId
    ) {
        assignmentService.assignTeacherToClass(userId, groupId, classId);
        return ResponseEntity.ok().build();
    }

    /**
     * Zwraca listę klas (przedmiotów), do których przypisany jest nauczyciel w danej grupie.
     *
     * @param userId  identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @return lista klas w postaci {@link ClassDTO}
     */
    @GetMapping("/user/{userId}/group/{groupId}/classes")
    public ResponseEntity<List<ClassDTO>> getAssignedClasses(
            @PathVariable int userId,
            @PathVariable int groupId) {
        List<SchoolClass> list = assignmentService.getAssignedClasses(userId, groupId);
        List<ClassDTO> dtos = list.stream()
                .map(c -> new ClassDTO(c.getId(), c.getClassName()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

}
