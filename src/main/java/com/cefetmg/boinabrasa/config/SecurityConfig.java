package com.cefetmg.boinabrasa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desabilita o CSRF para permitir POST, PUT e DELETE via frontend/Insomnia
            .csrf(csrf -> csrf.disable())
            
            // 2. Configura a liberação das rotas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 👈 LIBERA REQUISIÇÕES PRE-FLIGHT CORS
                .anyRequest().authenticated()
            )
            
            // 3. Ativa o protocolo HTTP Basic Auth
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}