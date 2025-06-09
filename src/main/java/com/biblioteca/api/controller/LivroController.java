package com.biblioteca.api.controller;

import com.biblioteca.api.dto.livro.LivroRequestDto;
import com.biblioteca.api.dto.livro.LivroResponseDto;
import com.biblioteca.api.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDto> criarLivro(@RequestBody LivroRequestDto livroDto) {
        LivroResponseDto novoLivro = livroService.criarLivro(livroDto);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivroPorId(@PathVariable Long id, @RequestBody LivroRequestDto livroDto) {
        LivroResponseDto livro = livroService.buscarLivroPorId(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> buscarTodosLivros() {
        List<LivroResponseDto> livros = livroService.buscarTodosLivros();
        return ResponseEntity.ok(livros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable Long id, @RequestBody LivroRequestDto livroDto) {
        LivroResponseDto livroAtualizado = livroService.atualizarLivro(id, livroDto);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
