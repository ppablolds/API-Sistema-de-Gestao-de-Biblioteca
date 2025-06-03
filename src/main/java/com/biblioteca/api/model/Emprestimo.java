package com.biblioteca.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime inicioEmprestimo;
    private LocalDateTime fimEmprestimo;

    public Emprestimo() {}

    public Emprestimo(Livro livro, Usuario usuario, LocalDateTime inicioEmprestimo, LocalDateTime fimEmprestimo) {
        this.livro = livro;
        this.usuario = usuario;
        this.inicioEmprestimo = inicioEmprestimo;
        this.fimEmprestimo = fimEmprestimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
