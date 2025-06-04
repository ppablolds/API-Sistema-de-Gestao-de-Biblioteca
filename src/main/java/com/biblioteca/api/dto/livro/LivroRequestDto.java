package com.biblioteca.api.dto.livro;

import com.biblioteca.api.util.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LivroRequestDto {

    @NotNull(message = "O ID do autor não pode ser nulo.")
    private Long autorId;

    @NotBlank(message = "Título do livro não pode estar em branco")
    @Size(max = 200, message = "Título do livro não pode exceder 200 caracteres")
    private String titulo;

    @NotBlank(message = "Editora do livro não pode estar em branco")
    @Size(max = 100, message = "Editora do livro não pode exceder 100 caracteres")
    private String editora;

    @NotNull(message = "Gênero do livro não pode ser nulo")
    private GeneroLivro generoLivro;

    public LivroRequestDto() {}

    public LivroRequestDto(Long autorId, String titulo, String editora, GeneroLivro generoLivro) {
        this.autorId = autorId;
        this.titulo = titulo;
        this.editora = editora;
        this.generoLivro = generoLivro;
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
}
