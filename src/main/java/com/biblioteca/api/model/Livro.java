package com.biblioteca.api.model;

import com.biblioteca.api.util.enums.GeneroLivro;
import com.biblioteca.api.util.enums.StatusLivro;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autorLivro;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String editora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeneroLivro generoLivro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLivro statusLivro;

    //private Emprestimo emprestimo;

    public Livro() {}

    public Livro(Autor autorLivro, String titulo, String editora, GeneroLivro generoLivro, StatusLivro statusLivro) {
        this.autorLivro = autorLivro;
        this.titulo = titulo;
        this.editora = editora;
        this.generoLivro = generoLivro;
        this.statusLivro = statusLivro.DISPONIVEL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(Autor autorLivro) {
        this.autorLivro = autorLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public GeneroLivro getGeneroLivro() {
        return generoLivro;
    }

    public void setGeneroLivro(GeneroLivro generoLivro) {
        this.generoLivro = generoLivro;
    }

    public StatusLivro getStatusLivro() {
        return statusLivro;
    }

    public void setStatusLivro(StatusLivro statusLivro) {
        this.statusLivro = statusLivro;
    }
}
