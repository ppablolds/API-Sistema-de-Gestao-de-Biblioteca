package com.biblioteca.api.repository;

import com.biblioteca.api.model.Livro;
import com.biblioteca.api.util.enums.GeneroLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // buscar livros por título, autor, etc.
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorLivro_NomeAutorContainingIgnoreCase(String nomeAutor);
    List<Livro> findByEditoraContainingIgnoreCase(String editora);
    List<Livro> findByGeneroLivro(GeneroLivro generoLivro);

    // verificar se um livro existe por título, autor, etc.
    Boolean existsByTitulo(String titulo);
    Boolean existsByAutorLivro_NomeAutor(String nomeAutor);
    Boolean existsByEditora(String editora);
    Boolean existsByGeneroLivro(GeneroLivro generoLivro);

    // Buscar um livro por título exato (se houver a necessidade de um título único)
    Optional<Livro> findByTitulo(String titulo);
}
