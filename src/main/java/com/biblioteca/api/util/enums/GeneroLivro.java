package com.biblioteca.api.util.enums;

public enum GeneroLivro {
    FICCAO("Ficção"),
    FANTASIA("Fantasia"),
    TERROR("Terror"),
    ROMANCE("Romance"),
    TECNOLOGIA("Tecnologia"),
    HISTORIA("História"),
    AVENTURA("Aventura"),
    DRAMA("Drama"),
    LITERATURA("Literatura"),
    BIOGRAFIA("Biografia");

    private final String descricao;

    GeneroLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
