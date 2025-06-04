package com.biblioteca.api.dto.autor;

public class AutorResponseDto {

    private Long id;
    private String nomeAutor;

    public AutorResponseDto() {}

    public AutorResponseDto(Long id, String nomeAutor) {
        this.id = id;
        this.nomeAutor = nomeAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}
