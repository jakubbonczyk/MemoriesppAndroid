package com.example.memoriessb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfiguracja zabezpieczeń aplikacji.
 * Ustawia domyślny mechanizm kodowania haseł oraz podstawową konfigurację zabezpieczeń HTTP.
 */
@Configuration
public class SecurityConfig {

    /**
     * Tworzy bean odpowiedzialny za kodowanie haseł przy użyciu algorytmu BCrypt.
     *
     * @return instancja BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Konfiguruje łańcuch filtrów zabezpieczeń.
     * Wyłącza ochronę CSRF i zezwala na dostęp do wszystkich endpointów.
     *
     * @param http obiekt konfiguracji HTTP
     * @return skonfigurowany SecurityFilterChain
     * @throws Exception w przypadku błędów konfiguracji
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .build();
    }
}
