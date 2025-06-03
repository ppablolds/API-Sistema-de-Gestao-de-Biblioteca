package com.biblioteca.api.repository;

import com.biblioteca.api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // buscar autores por nomeAutor:
    List<Autor> findByNomeAutorContainingIgnoreCase(String nomeAutor);

    // verificar se um autor existe por nome do autor:
    Boolean existsByNomeAutor(String autor);

    // buscar um autor por nome do autor:
    Optional<Autor> findByNomeAutor(String nomeAutor);
}
