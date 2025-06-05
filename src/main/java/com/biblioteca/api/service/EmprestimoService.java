package com.biblioteca.api.service;

import com.biblioteca.api.dto.emprestimo.EmprestimoRequestDto;
import com.biblioteca.api.dto.emprestimo.EmprestimoResponseDto;
import com.biblioteca.api.exception.BadRequestException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.model.Emprestimo;
import com.biblioteca.api.model.Livro;
import com.biblioteca.api.model.Usuario;
import com.biblioteca.api.repository.EmprestimoRepository;
import com.biblioteca.api.repository.LivroRepository;
import com.biblioteca.api.repository.UsuarioRepository;
import com.biblioteca.api.util.enums.StatusLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             LivroRepository livroRepository,
                             UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Realizar emprestimo
    @Transactional
    public EmprestimoResponseDto realizarEmprestimo(EmprestimoRequestDto emprestimoDto) {
        // Buscar Livro e Usuário pelas IDs
        Livro livro = livroRepository.findById(emprestimoDto.getLivroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + emprestimoDto.getLivroId()));

        Usuario usuario = usuarioRepository.findById(emprestimoDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + emprestimoDto.getUsuarioId()));

        // Verificar se o livro está DISPONIVEL
        if (livro.getStatusLivro() != StatusLivro.DISPONIVEL) {
            throw new BadRequestException("O livro '" + livro.getTitulo() + "' não está disponível para empréstimo. Status atual: " + livro.getStatusLivro().getDescricao());
        }

        // Criar o Emprestimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setInicioEmprestimo(emprestimoDto.getInicioEmprestimo() != null ? emprestimoDto.getInicioEmprestimo() : LocalDateTime.now());

        // Salvar o emprestimo
        Emprestimo novoEmprestimo = emprestimoRepository.save(emprestimo);

        return new EmprestimoResponseDto(novoEmprestimo.getId(),
                                        novoEmprestimo.getLivro().getId(),
                                        novoEmprestimo.getUsuario().getId(),
                                        novoEmprestimo.getInicioEmprestimo(),
                                        novoEmprestimo.getFimEmprestimo());
    }

    // Realiza a devolução
    @Transactional
    public EmprestimoResponseDto registrarDevolucao(Long emprestimoId) {
        // Buscar o Emprestimo existente
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado com ID: " + emprestimoId));

        // Verificar se o empréstimo já foi finalizado
        if (emprestimo.getFimEmprestimo() != null) {
            throw new BadRequestException("Este empréstimo já foi finalizado em: " + emprestimo.getFimEmprestimo());
        }

        // Definir a data/hora da devolução
        emprestimo.setFimEmprestimo(LocalDateTime.now());

        // Salvar o Emprestimo atualizado
        Emprestimo emprestimoDevolvido = emprestimoRepository.save(emprestimo);

        // Atualizar o status do Livro para DISPONIVEL
        Livro livro = emprestimoDevolvido.getLivro();
        livro.setStatusLivro(StatusLivro.DISPONIVEL);
        livroRepository.save(livro);

        // Retornar o DTO de resposta
        return new EmprestimoResponseDto(emprestimoDevolvido.getId(),
                                        emprestimoDevolvido.getLivro().getId(),
                                        emprestimoDevolvido.getUsuario().getId(),
                                        emprestimoDevolvido.getInicioEmprestimo(),
                                        emprestimoDevolvido.getFimEmprestimo());
    }

    // Buscar emprestimo por id
    @Transactional(readOnly = true)
    public EmprestimoResponseDto buscarEmprestimoPorId(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado com ID: " + id));

        return new EmprestimoResponseDto(emprestimo.getId(),
                                        emprestimo.getLivro().getId(),
                                        emprestimo.getUsuario().getId(),
                                        emprestimo.getInicioEmprestimo(),
                                        emprestimo.getFimEmprestimo());
    }

    // Buscar todos os emprestimos
    @Transactional(readOnly = true)
    public List<EmprestimoResponseDto> buscarTodosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        return emprestimos.stream()
                .map(emprestimo -> new EmprestimoResponseDto(emprestimo.getId(),
                        emprestimo.getLivro().getId(),
                        emprestimo.getUsuario().getId(),
                        emprestimo.getInicioEmprestimo(),
                        emprestimo.getFimEmprestimo()))
                .collect(Collectors.toList());
    }

    // Buscar por emprestimos ativos
    @Transactional(readOnly = true)
    public List<EmprestimoResponseDto> buscarPorEmprestimosAtivos() {
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.findByFimEmprestimoIsNull();

        return emprestimosAtivos.stream()
                .map(emprestimo -> new EmprestimoResponseDto(emprestimo.getId(),
                        emprestimo.getLivro().getId(),
                        emprestimo.getUsuario().getId(),
                        emprestimo.getInicioEmprestimo(),
                        emprestimo.getFimEmprestimo()))
                .collect(Collectors.toList());
    }

    // Buscar emprestimo por usuário
    @Transactional(readOnly = true)
    public List<EmprestimoResponseDto> buscarEmprestimosPorUsuario(Long usuarioId) {
        // Verificar se o usuário existe antes de buscar
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }
        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioId(usuarioId);
        return emprestimos.stream()
                .map(emprestimo -> new EmprestimoResponseDto(
                        emprestimo.getId(),
                        emprestimo.getLivro().getId(),
                        emprestimo.getUsuario().getId(),
                        emprestimo.getInicioEmprestimo(),
                        emprestimo.getFimEmprestimo()
                ))
                .collect(Collectors.toList());
    }

    // Buscar emprestimo por livro
    @Transactional(readOnly = true)
    public List<EmprestimoResponseDto> buscarEmprestimosPorLivro(Long livroId) {
        // Verificar se o livro existe antes de buscar
        if (!livroRepository.existsById(livroId)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + livroId);
        }
        List<Emprestimo> emprestimos = emprestimoRepository.findByLivroId(livroId);
        return emprestimos.stream()
                .map(emprestimo -> new EmprestimoResponseDto(
                        emprestimo.getId(),
                        emprestimo.getLivro().getId(),
                        emprestimo.getUsuario().getId(),
                        emprestimo.getInicioEmprestimo(),
                        emprestimo.getFimEmprestimo()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarEmprestimo(Long id) {
        // Verificar se o emprestimo existe
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado com ID: " + id));

        // Só pode deletar empréstimos que já foram devolvidos
        if (emprestimo.getFimEmprestimo() == null) {
            throw new BadRequestException("Não é possível deletar um empréstimo ativo. Registre a devolução primeiro.");
        }

        // Deletar emprestimo
        emprestimoRepository.deleteById(id);
    }

}
