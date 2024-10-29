package com.learning.security_spring.domain.repository;

import com.learning.security_spring.domain.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {

    Optional<Grupo> findByNome(String nomeGrupo);
}
