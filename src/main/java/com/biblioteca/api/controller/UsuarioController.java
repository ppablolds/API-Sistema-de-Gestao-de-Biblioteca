package com.biblioteca.api.controller;

import com.biblioteca.api.dto.usuario.UsuarioRequestDto;
import com.biblioteca.api.dto.usuario.UsuarioResponseDto;
import com.biblioteca.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@RequestBody UsuarioRequestDto usuarioDto) {
        UsuarioResponseDto novoUsuario = usuarioService.criarUsuario(usuarioDto);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(@PathVariable Long id, @RequestBody UsuarioRequestDto usuarioDto) {
        UsuarioResponseDto usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> buscarTodosUsuario() {
        List<UsuarioResponseDto> usuarios = usuarioService.buscarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDto usuarioDto) {
        UsuarioResponseDto usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
