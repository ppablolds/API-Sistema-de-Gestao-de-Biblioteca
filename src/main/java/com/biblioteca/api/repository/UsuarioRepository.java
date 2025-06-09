package com.biblioteca.api.repository;

import com.biblioteca.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // buscar um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // buscar um usuario pelo username
    Optional<Usuario> findByUsername(String username);

    // verificar se um usuário existe pelo email
    Boolean existsByEmail(String email);

    // verificar se um usuário existe pelo username
    Boolean existsByUsername(String username);
}
