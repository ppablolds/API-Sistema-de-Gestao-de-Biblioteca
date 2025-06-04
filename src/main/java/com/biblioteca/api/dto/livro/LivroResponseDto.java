package com.biblioteca.api.dto.livro;

import com.biblioteca.api.util.enums.GeneroLivro;
import com.biblioteca.api.util.enums.StatusLivro;

public class LivroResponseDto {

    private Long id;
    private Long autorId;
    private String titulo;
    private String editora;
    private GeneroLivro generoLivro;
    private StatusLivro statusLivro;

    public LivroResponseDto() {}

    public LivroResponseDto(Long id, Long autorId, String titulo, String editora, GeneroLivro generoLivro, StatusLivro statusLivro) {
        this.id = id;
        this.autorId = autorId;
        this.titulo = titulo;
        this.editora = editora;
        this.generoLivro = generoLivro;
        this.statusLivro = statusLivro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
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
