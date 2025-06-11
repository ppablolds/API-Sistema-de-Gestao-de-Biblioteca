package com.biblioteca.api.service;

import com.biblioteca.api.config.security.JwtUtils;
import com.biblioteca.api.dto.auth.AuthRequestDto;
import com.biblioteca.api.dto.auth.AuthResponseDto;
import com.biblioteca.api.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired

    public AuthService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequest) {
        try {
            // Tenta autenticar o usuário usando o AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Lança uma exceção se a autenticação falhar (username/password incorretos)
            throw new BadCredentialsException("Credenciais inválidas.", e);
        }

        // Se a autenticação foi bem-sucedida, carrega os detalhes do usuário
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // Gera o token JWT
        final String jwt = jwtUtils.generateToken(userDetails);

        // Retorna o token na resposta
        return new AuthResponseDto(jwt);
    }
}
