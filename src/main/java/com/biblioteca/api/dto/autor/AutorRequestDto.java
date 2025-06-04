package com.biblioteca.api.dto.autor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AutorRequestDto {

    @NotBlank
    @Size(max = 100, message = "O nome do autor deve ter no máximo 100 caracteres.")
    private String nomeAutor;

    public AutorRequestDto() {}

    public AutorRequestDto(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}
