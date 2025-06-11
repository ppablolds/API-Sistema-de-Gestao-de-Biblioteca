package com.biblioteca.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para APIs REST
                .headers(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // APIs RESTful são stateless
                .authorizeHttpRequests(authorize -> authorize
                        // Endpoints públicos de autenticação
                        .requestMatchers("/auth/**",
                                                "/error",
                                                "/h2-console/**",
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/swagger-ui.html").permitAll() // Permite acesso a /auth/login e /auth/register
                        // Outros endpoints protegidos (exigem autenticação)
                        .requestMatchers("/autores/**").authenticated() // Exige autenticação para /autores
                        .requestMatchers("/livros/**").authenticated() // Exige autenticação para /livros
                        .requestMatchers("/usuarios/**").authenticated() // Exige autenticação para /usuarios
                        .requestMatchers("/emprestimos/**").authenticated() // Exige autenticação para /emprestimos
                        .anyRequest().authenticated() // Qualquer outra requisição deve ser autenticada
                )
                .authenticationProvider(authenticationProvider()) // Adiciona o provedor de autenticação
                // Adiciona o filtro JWT antes do filtro padrão de autenticação de usuário/senha
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF (necessário para Postman sem tokens)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Permite todas as requisições sem autenticação
                );
        return http.build();
    }*/
}
