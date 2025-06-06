package com.biblioteca.api.controller;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import com.biblioteca.api.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorResponseDto> criarAutor(@RequestBody AutorRequestDto autorDto) {
        AutorResponseDto novoAutor = autorService.criarAutor(autorDto);
        return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDto> buscarAutorPorId(@PathVariable Long id) {
        AutorResponseDto autor = autorService.buscarAutorPorId(id);
        return ResponseEntity.ok(autor);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDto>> buscarTodosAutores() {
        List<AutorResponseDto> autores = autorService.buscarTodosAutores();
        return ResponseEntity.ok(autores);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<AutorResponseDto> atualizarAutor(@PathVariable Long id, @RequestBody AutorRequestDto autorDto) {
        AutorResponseDto autorAtualizado = autorService.atualizarAutor(id, autorDto);
        return ResponseEntity.ok(autorAtualizado);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        autorService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }
}
