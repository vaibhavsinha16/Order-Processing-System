package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the Order Processing System with JWT authentication.
 * 
 * This class configures Spring Security to use JWT tokens for authentication
 * while allowing access to public endpoints and Swagger UI.
 * 
 * @author Order Processing System
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    /**
     * Configures the security filter chain for HTTP requests with JWT authentication.
     * 
     * This method sets up security rules for different endpoints:
     * - Public endpoints: Static resources, authentication, products, cart operations
     * - Swagger UI: API documentation interface
     * - Protected endpoints: Orders and other authenticated operations
     * 
     * @param http HttpSecurity object to configure
     * @return Configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index.html", "/login.html", "/my-orders.html", "/static/**", 
                    "/products", "/auth/**", "/addresses", "/cart/**",
                    "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers("/orders/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());
        
        return http.build();
    }
} 