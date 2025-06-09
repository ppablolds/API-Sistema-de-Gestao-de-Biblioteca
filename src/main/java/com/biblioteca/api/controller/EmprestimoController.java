package com.biblioteca.api.controller;

import com.biblioteca.api.dto.emprestimo.EmprestimoRequestDto;
import com.biblioteca.api.dto.emprestimo.EmprestimoResponseDto;
import com.biblioteca.api.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/realizar-emprestimo")
    public ResponseEntity<EmprestimoResponseDto> criarEmprestimo(@RequestBody EmprestimoRequestDto emprestimoDto) {
        EmprestimoResponseDto novoEmprestimo = emprestimoService.realizarEmprestimo(emprestimoDto);
        return new ResponseEntity<>(novoEmprestimo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDto> buscarEmprestimoPorId(@PathVariable Long id) {
        EmprestimoResponseDto emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDto>> buscarTodosEmprestimo() {
        List<EmprestimoResponseDto> emprestimos = emprestimoService.buscarTodosEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/emprestimos-ativos")
    public ResponseEntity<List<EmprestimoResponseDto>> buscarEmprestimosAtivos() {
        List<EmprestimoResponseDto> emprestimosAtivos = emprestimoService.buscarPorEmprestimosAtivos();
        return ResponseEntity.ok(emprestimosAtivos);
    }

    /*
    @GetMapping("/emprestimos-usuarios/{id}")
    public ResponseEntity<List<EmprestimoResponseDto>> buscarEmprestimosPorUsuario(@PathVariable Long usuarioId) {
        List<EmprestimoResponseDto> emprestimosPorUsuario = emprestimoService.buscarEmprestimosPorUsuario(usuarioId);
        return ResponseEntity.ok(emprestimosPorUsuario);
    }

    @GetMapping("/emprestimos-livro/{id}")
    public ResponseEntity<List<EmprestimoResponseDto>> buscarEmprestimosPorLivro(@PathVariable Long livroId) {
        List<EmprestimoResponseDto> emprestimosPorLivro = emprestimoService.buscarEmprestimosPorLivro(livroId);
        return ResponseEntity.ok(emprestimosPorLivro);
    }*/

    @PostMapping("/realizar-devolucao/{id}")
    public ResponseEntity<EmprestimoResponseDto> registrarDevolucao(@PathVariable Long emprestimoId) {
        EmprestimoResponseDto devolucaoEmprestimo = emprestimoService.registrarDevolucao(emprestimoId);
        return new ResponseEntity<>(devolucaoEmprestimo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
