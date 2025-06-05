package com.biblioteca.api.repository;

import com.biblioteca.api.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário
    // Por exemplo, buscar empréstimos por usuário ou livro, etc.
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByLivroId(Long livroId);

    // Verificar se um empréstimo existe por livro ou usuário
    Boolean existsByUsuarioId(Long usuarioId);
    Boolean existsByLivroId(Long livroId);

    // Buscar todos os empréstimos ativos (fimEmprestimo é nulo) para um determinado livro
    Boolean findByLivroIdAndFimEmprestimoIsNull(Long livroId);

    // Buscar todos os empréstimos ativos (fimEmprestimo é nulo) para um determinado usuário
    List<Emprestimo> findByUsuarioIdAndFimEmprestimoIsNull(Long usuarioId);

    // Verificar se um livro está atualmente emprestado
    Boolean existsByLivroIdAndFimEmprestimoIsNull(Long livroId);

    List<Emprestimo> findByInicioEmprestimoBetween(LocalDateTime start, LocalDateTime end);
    List<Emprestimo> findByFimEmprestimoIsNull();
}
