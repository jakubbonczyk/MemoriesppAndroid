package com.example.memoriessb.controller;

import com.example.memoriessb.DTO.ClassDTO;
import com.example.memoriessb.DTO.CreateClassRequest;
import com.example.memoriessb.etities.SchoolClass;
import com.example.memoriessb.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST odpowiedzialny za zarządzanie przedmiotami (klasami).
 * Umożliwia tworzenie nowych klas oraz pobieranie listy wszystkich klas.
 */
@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassRepository schoolClassRepository;

    /**
     * Tworzy nowy przedmiot (klasę) na podstawie przesłanej nazwy.
     *
     * @param request obiekt zawierający nazwę nowej klasy
     * @return utworzony przedmiot w postaci {@link ClassDTO}
     */
    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody CreateClassRequest request) {
        SchoolClass sc = new SchoolClass();
        sc.setClassName(request.getClassName());
        SchoolClass saved = schoolClassRepository.save(sc);
        return ResponseEntity.ok(new ClassDTO(saved.getId(), saved.getClassName()));
    }

    /**
     * Zwraca listę wszystkich przedmiotów (klas) zarejestrowanych w systemie.
     *
     * @return lista klas w postaci {@link ClassDTO}
     */
    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> list = schoolClassRepository.findAll().stream()
                .map(sc -> new ClassDTO(sc.getId(), sc.getClassName()))
                .toList();
        return ResponseEntity.ok(list);
    }
}
