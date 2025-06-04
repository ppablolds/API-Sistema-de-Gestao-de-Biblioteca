package com.biblioteca.api.dto.emprestimo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EmprestimoRequestDto {

    @NotNull(message = "O ID do livro é obrigatório.")
    private Long livroId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "A data de início do empréstimo é obrigatória.")
    private LocalDateTime inicioEmprestimo;

    public EmprestimoRequestDto() {}

    public EmprestimoRequestDto(Long livroId, Long usuarioId, LocalDateTime inicioEmprestimo) {
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.inicioEmprestimo = inicioEmprestimo;
    }

    public LocalDateTime getInicioEmprestimo() {
        return inicioEmprestimo;
    }

    public void setInicioEmprestimo(LocalDateTime inicioEmprestimo) {
        this.inicioEmprestimo = inicioEmprestimo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }
}
