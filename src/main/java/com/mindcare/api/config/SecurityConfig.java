package com.mindcare.api.config;

import com.mindcare.api.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importação adicionada
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // O Spring injetará automaticamente o JwtAuthenticationFilter aqui
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita a proteção CSRF para APIs REST sem sessão
            .csrf(csrf -> csrf.disable())
            // Define as regras de autorização para diferentes requisições HTTP
            .authorizeHttpRequests(auth -> auth
                // Permite acesso público a estes endpoints
                .requestMatchers("/login", "/signuppage", "/usuarios", "/usuarios/me").permitAll()
                // Permite acesso público à documentação do Swagger UI e OpenAPI
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Qualquer outra requisição exige autenticação
                .anyRequest().authenticated()
            )
            // Configura a gestão de sessão para ser STATELESS (sem estado), comum em APIs REST
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Adiciona o filtro JWT antes do filtro padrão de autenticação por usuário/senha do Spring Security
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Expõe o AuthenticationManager, que é usado para autenticar usuários
        return config.getAuthenticationManager();
    }

    // --- ESTE É O MÉTODO ADICIONADO QUE RESOLVE O PROBLEMA ---
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // Define e retorna uma instância de BCryptPasswordEncoder como um bean do Spring.
        // Isso permite que o Spring injete essa instância em outras classes (como UserService)
        // que precisam de um encoder de senhas.
        return new BCryptPasswordEncoder();
    }
}