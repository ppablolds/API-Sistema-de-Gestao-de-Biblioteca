package com.biblioteca.api.service;

import com.biblioteca.api.dto.livro.LivroRequestDto;
import com.biblioteca.api.dto.livro.LivroResponseDto;
import com.biblioteca.api.exception.BadRequestException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.model.Autor;
import com.biblioteca.api.model.Livro;
import com.biblioteca.api.repository.AutorRepository;
import com.biblioteca.api.repository.EmprestimoRepository;
import com.biblioteca.api.repository.LivroRepository;
import com.biblioteca.api.util.enums.StatusLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EmprestimoRepository emprestimoRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    // Criar Livro
    @Transactional
    public LivroResponseDto criarLivro(LivroRequestDto livroDto) {
        // Verifica se o Autor existe
        Autor autor = autorRepository.findById(livroDto.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com ID: " + livroDto.getAutorId()));

        // Verifica se existe livro com o mesmo nome
        if (livroRepository.existsByTitulo(livroDto.getTitulo())) {
            throw new BadRequestException("Já existe um livro com o nome " + livroDto.getTitulo());
        }

        // Criar livro
        Livro livro = new Livro();
        livro.setTitulo(livroDto.getTitulo());
        livro.setEditora(livroDto.getEditora());
        livro.setGeneroLivro(livroDto.getGeneroLivro());
        livro.setAutorLivro(autor);
        livro.setStatusLivro(StatusLivro.DISPONIVEL);

        Livro livroSaved = livroRepository.save(livro);

        return new LivroResponseDto(livroSaved.getId(),
                                    livroSaved.getAutorLivro().getId(),
                                    livroSaved.getTitulo(),
                                    livroSaved.getEditora(),
                                    livroSaved.getGeneroLivro(),
                                    livroSaved.getStatusLivro());
    }

    // Buscar livro por Id
    @Transactional(readOnly = true)
    public LivroResponseDto buscarLivroPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        return new LivroResponseDto(livro.getId(),
                livro.getAutorLivro().getId(),
                livro.getTitulo(),
                livro.getEditora(),
                livro.getGeneroLivro(),
                livro.getStatusLivro());
    }

    // Buscar todos o livros
    @Transactional(readOnly = true)
    public List<LivroResponseDto> buscarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream()
                .map(livro -> new LivroResponseDto( livro.getId(),
                        livro.getAutorLivro().getId(),
                        livro.getTitulo(),
                        livro.getEditora(),
                        livro.getGeneroLivro(),
                        livro.getStatusLivro()))
                .collect(Collectors.toList());
    }

    // Atualizar livro
    @Transactional
    public LivroResponseDto atualizarLivro(Long id, LivroRequestDto livroDto) {
        // Verificar se o livro a ser atualizado existe
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        //Verificar se o autor do DTO existe
        if (!livroExistente.getAutorLivro().getId().equals(livroDto.getAutorId())) {
            Autor novoAutor = autorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Novo autor não encontrado com ID: " + livroDto.getAutorId()));
            livroExistente.setAutorLivro(novoAutor);
        }

        // Verificar se o novo título já é usado por outro livro
        if (!livroDto.getTitulo().equals(livroExistente.getTitulo()) &&
            livroRepository.existsByTitulo(livroDto.getTitulo())) {
            throw new BadRequestException("O título '" + livroDto.getTitulo() + "' já está em uso por outro livro.");
        }

        // Atualizar os campos do livro
        livroExistente.setTitulo(livroDto.getTitulo());
        livroExistente.setEditora(livroDto.getEditora());
        livroExistente.setGeneroLivro(livroDto.getGeneroLivro());

        // Salvar as alterações
        Livro livroUpdated = livroRepository.save(livroExistente);

        return new LivroResponseDto(livroUpdated.getId(),
                                    livroUpdated.getAutorLivro().getId(),
                                    livroUpdated.getTitulo(),
                                    livroUpdated.getEditora(),
                                    livroUpdated.getGeneroLivro(),
                                    livroUpdated.getStatusLivro());
    }

    // Deletar Livro
    @Transactional
    public void deletarLivro(Long id) {
        // Verificar se o livro existe
        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + id);
        }

        // Verificar se o livro está atualmente emprestado
        if (emprestimoRepository.existsByLivroIdAndFimEmprestimoIsNull(id)) {
            throw new BadRequestException("Não é possível deletar o livro. Ele está atualmente emprestado.");
        }

        // Deletar Livro
        livroRepository.deleteById(id);
    }
}
