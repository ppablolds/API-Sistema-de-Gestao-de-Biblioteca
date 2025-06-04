package com.biblioteca.api.dto.emprestimo;

import java.time.LocalDateTime;

public class EmprestimoResponseDto {

    private Long id;
    private Long livroId;
    private Long usuarioId;
    private LocalDateTime inicioEmprestimo;
    private LocalDateTime fimEmprestimo;

    public EmprestimoResponseDto() {}

    public EmprestimoResponseDto(Long id, Long livroId, Long usuarioId, LocalDateTime inicioEmprestimo, LocalDateTime fimEmprestimo) {
        this.id = id;
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.inicioEmprestimo = inicioEmprestimo;
        this.fimEmprestimo = fimEmprestimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getInicioEmprestimo() {
        return inicioEmprestimo;
    }

    public void setInicioEmprestimo(LocalDateTime inicioEmprestimo) {
        this.inicioEmprestimo = inicioEmprestimo;
    }

    public LocalDateTime getFimEmprestimo() {
        return fimEmprestimo;
    }

    public void setFimEmprestimo(LocalDateTime fimEmprestimo) {
        this.fimEmprestimo = fimEmprestimo;
    }
}
