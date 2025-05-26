package com.example.memoriessb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Globalny przechwytujący wyjątki dla aplikacji Spring.
 * Obsługuje różne typy wyjątków i zwraca odpowiednie kody HTTP oraz komunikaty.
 */
@RestControllerAdvice
public class GlobalExceptionCatcher {

    /**
     * Obsługuje wyjątki typu IllegalArgumentException.
     *
     * @param ex wyjątek typu IllegalArgumentException
     * @return odpowiedź HTTP 400 (Bad Request) z komunikatem wyjątku
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Obsługuje wyjątki typu RuntimeException.
     *
     * @param ex wyjątek typu RuntimeException
     * @return odpowiedź HTTP 500 (Internal Server Error) z komunikatem wyjątku
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Obsługuje wszystkie inne wyjątki.
     *
     * @param ex dowolny wyjątek
     * @return odpowiedź HTTP 500 z ogólnym komunikatem o błędzie
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
