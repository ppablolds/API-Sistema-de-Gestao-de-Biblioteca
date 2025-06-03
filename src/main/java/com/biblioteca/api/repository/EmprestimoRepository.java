package com.biblioteca.api.repository;

import com.biblioteca.api.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário
    // Por exemplo, buscar empréstimos por usuário ou livro, etc.
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByLivroId(Long livroId);

    // Verificar se um empréstimo existe por livro ou usuário
    Boolean existsByUsuarioId(Long usuarioId);
    Boolean existsByLivroId(Long livroId);
}
