package com.biblioteca.api.util.enums;

public enum StatusLivro {
    DISPONIVEL("Disponível"),
    EMPRESTADO("Emprestado"),
    RESERVADO("Reservado"),
    PERDIDO("Perdido");

    private final String descricao;

    StatusLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
