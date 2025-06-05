package com.biblioteca.api.service;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import com.biblioteca.api.exception.BadRequestException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.model.Autor;
import com.biblioteca.api.repository.AutorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional
    public AutorResponseDto criarAutor(AutorRequestDto autorDto) {
        // Verifica se o autor já existe pelo nome
        if (autorRepository.existsByNomeAutor(autorDto.getNomeAutor())) {
            throw new BadRequestException("Autor já cadastrado com o nome: " + autorDto.getNomeAutor());
        }

        // Criação do Autor
        Autor autor = new Autor();
        autor.setNomeAutor(autorDto.getNomeAutor());

        // Salva o autor no repositório
        return new AutorResponseDto(autor.getId(), autor.getNomeAutor());
    }

    // Buscar autor por ID
    @Transactional(readOnly = true) // Apenas leitura, otimiza transação
    public AutorResponseDto buscarAutorPorId(Long id) {
        // Busca o autor pelo ID, ou lança uma exceção se não encontrado
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Autor não encontrado com ID: " + id));

        // Retornando o DTO de resposta
        return new AutorResponseDto(autor.getId(), autor.getNomeAutor());
    }

    // Buscar todos os autores
    @Transactional(readOnly = true) // Apenas leitura, otimiza transação
    public List<AutorResponseDto> buscarTodosAutores() {
        // Busca todos os autores do repositório
        List<Autor> autores = autorRepository.findAll();

        // Converte a lista de autores para DTOs de resposta
        return autores.stream()
                .map(autor -> new AutorResponseDto(autor.getId(), autor.getNomeAutor()))
                .collect(Collectors.toList());
    }

    /*
    // Atualizar autor
    @Transactional
    public AutorResponseDto atualizarAutor(Long id, AutorRequestDto autorDto) {
        // Busca o autor pelo ID
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Autor não encontrado com ID: " + id));

        // Verifica se o nome do autor já existe
        if (!autorDto.getNomeAutor().equals(autor.getNomeAutor()) &&
        autorRepository.existsByNomeAutor(autorDto.getNomeAutor())) {
            throw new BadRequestException("Autor já cadastrado com o nome: " + autorDto.getNomeAutor());
        }

        // Atualiza os dados do autor
        autor.setNomeAutor(autorDto.getNomeAutor());

        // Salva as alterações no repositório
        Autor autorAtualizado = autorRepository.save(autor);

        // Retorna o DTO de resposta com os dados atualizados
        return new AutorResponseDto(autorAtualizado.getId(), autorAtualizado.getNomeAutor());
    }*/

    // Deletar autor
    @Transactional
    public void deletarAutor(Long id) {
        // Verificar se o autor existe
        if (autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor não encontrado com ID: " + id);
        }

        // Deletar o Autor
        autorRepository.deleteById(id);
    }
}
