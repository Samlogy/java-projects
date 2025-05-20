package com.example.spring.webflux.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtSecurityContextRepository securityContextRepository;

    public SecurityConfig(JwtSecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchanges -> exchanges
                        // Permit public access to login and registration
                        .pathMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
                        // Endpoints under /admin/** require ADMIN role
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        // Endpoints under /moderator/** require MODERATOR or ADMIN role
                        .pathMatchers("/moderator/**").hasAnyRole("MODERATOR", "ADMIN")
                        // All other endpoints require authentication
                        .anyExchange().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
