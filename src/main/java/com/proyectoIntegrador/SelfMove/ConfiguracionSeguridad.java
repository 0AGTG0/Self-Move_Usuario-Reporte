package com.proyectoIntegrador.SelfMove;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Indica a Spring que esta clase contiene definiciones de beans
public class ConfiguracionSeguridad {

    // Define un bean para PasswordEncoder (BCryptPasswordEncoder es una implementación)
    // Spring inyectará automáticamente esta instancia donde se solicite PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}