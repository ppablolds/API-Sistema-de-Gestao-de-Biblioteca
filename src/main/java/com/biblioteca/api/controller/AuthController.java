package com.biblioteca.api.controller;

import com.biblioteca.api.dto.auth.AuthRequestDto;
import com.biblioteca.api.dto.auth.AuthResponseDto;
import com.biblioteca.api.dto.usuario.UsuarioRequestDto;
import com.biblioteca.api.dto.usuario.UsuarioResponseDto;
import com.biblioteca.api.service.AuthService;
import com.biblioteca.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        AuthResponseDto authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> register(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto novoUsuario = usuarioService.criarUsuario(usuarioRequestDto);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
}
