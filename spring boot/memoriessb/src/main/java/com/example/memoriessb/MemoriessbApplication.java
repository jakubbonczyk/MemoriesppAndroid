package com.example.memoriessb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Główna klasa uruchamiająca aplikację Spring Boot.
 * Inicjalizuje kontekst aplikacji i opcjonalnie generuje zakodowane hasło testowe.
 */
@SpringBootApplication
public class MemoriessbApplication {

    /**
     * Punkt wejścia do aplikacji.
     *
     * @param args argumenty linii poleceń (nieużywane)
     */
    public static void main(String[] args) {

        SpringApplication.run(MemoriessbApplication.class, args);

        System.out.println(new BCryptPasswordEncoder().encode("test123"));

    }

}
