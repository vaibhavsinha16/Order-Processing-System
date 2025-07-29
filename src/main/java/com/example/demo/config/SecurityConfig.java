package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Order Processing System.
 * 
 * This class configures Spring Security to allow access to public endpoints
 * while protecting authenticated endpoints. It also configures access to
 * Swagger UI and API documentation.
 * 
 * @author Order Processing System
 * @version 1.0
 * @since 2024
 */
@Configuration
public class SecurityConfig {
    
    /**
     * Configures the security filter chain for HTTP requests.
     * 
     * This method sets up security rules for different endpoints:
     * - Public endpoints: Static resources, authentication, products, cart operations
     * - Swagger UI: API documentation interface
     * - All other requests: Temporarily permitted for development
     * 
     * @param http HttpSecurity object to configure
     * @return Configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index.html", "/login.html", "/static/**", 
                    "/products", "/auth/**", "/addresses", "/cart/**",
                    "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().permitAll() // Temporarily allow all other requests
            )
            .formLogin().disable(); // disable default login
        return http.build();
    }
} 