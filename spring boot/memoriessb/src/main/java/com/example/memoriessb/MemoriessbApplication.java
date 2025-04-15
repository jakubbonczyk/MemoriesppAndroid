package com.example.memoriessb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MemoriessbApplication {

    public static void main(String[] args) {

        SpringApplication.run(MemoriessbApplication.class, args);

        System.out.println(new BCryptPasswordEncoder().encode("test123"));

    }

}
