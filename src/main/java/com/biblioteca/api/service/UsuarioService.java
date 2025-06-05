package com.biblioteca.api.service;

import com.biblioteca.api.dto.usuario.UsuarioRequestDto;
import com.biblioteca.api.dto.usuario.UsuarioResponseDto;
import com.biblioteca.api.exception.BadRequestException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.model.Usuario;
import com.biblioteca.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Criar um novo usuário
    @Transactional // Garante que a operação seja atômica
    public UsuarioResponseDto criarUsuario(UsuarioRequestDto usuarioDto) {
        // Lógica de negócio: verificar se o usuário ou email já existe
        if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            throw new BadRequestException("Nome de usuário já está em uso.");
        }
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new BadRequestException("Email já está em uso.");
        }

        // Criação do usuário
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword())); // Criptografando a senha

        Usuario usuarioSaved = usuarioRepository.save(usuario);

        // Retornando o DTO de resposta
        return new UsuarioResponseDto(usuarioSaved.getId(), usuarioSaved.getUsername(), usuarioSaved.getEmail());
    }

    // Buscar usuário por ID
    @Transactional(readOnly = true) // Apenas leitura, otimiza transação
    // Buscar a entidade pelo ID, ou lançar exceção se não encontrado
    public UsuarioResponseDto buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Retornando o DTO de resposta
        return new UsuarioResponseDto(usuario.getId(), usuario.getUsername(), usuario.getEmail());
    }

    // Buscar todos os usuários
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> buscarTodosUsuarios() {
        // Buscar todas as entidades
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Mapear cada entidade para um Response DTO
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDto(usuario.getId(), usuario.getUsername(), usuario.getEmail()))
                .collect(Collectors.toList());
    }

    // Atualizar usuário
    @Transactional
    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioRequestDto usuarioDto) {
        // Verificar se o usuário existe
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Lógica de negócio: Atualizar campos. Senha geralmente é atualizada separadamente.
        // Se o username ou email for alterado, verificar unicidade novamente (com cuidado para não conflitar consigo mesmo)
        if (!usuarioDto.getUsername().equals(usuarioExistente.getUsername()) &&
        usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            throw new BadRequestException("Nome de usuário já está em uso.");
        }
        if (!usuarioDto.getEmail().equals(usuarioExistente.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new BadRequestException("Email já está em uso.");
        }

        usuarioExistente.setUsername(usuarioDto.getUsername());
        usuarioExistente.setEmail(usuarioDto.getEmail());

        Usuario usuarioUpdated = usuarioRepository.save(usuarioExistente);
        return new UsuarioResponseDto(usuarioUpdated.getId(), usuarioUpdated.getUsername(), usuarioUpdated.getEmail());
    }

    // Deletar usuário
    @Transactional
    public void deletarUsuario(Long id) {
        // Verificar se o usuário existe antes de deletar
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }

        // Deletar o usuário

        // Lógica de negócio: O que acontece com os empréstimos deste usuário?
        // - Excluir empréstimos? (Cascade.ALL no relacionamento com Usuario, ou lógica aqui)
        // - Marcar empréstimos como 'usuário removido'?
        // Para simplicidade inicial, apenas deletamos.
        usuarioRepository.deleteById(id);
    }
}
