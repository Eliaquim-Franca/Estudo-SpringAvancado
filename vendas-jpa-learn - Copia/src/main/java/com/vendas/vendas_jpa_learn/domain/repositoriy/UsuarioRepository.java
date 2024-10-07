package com.vendas.vendas_jpa_learn.domain.repositoriy;

import com.vendas.vendas_jpa_learn.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLogin(String longin);
}
