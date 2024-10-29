package com.learning.security_spring.domain.repository;

import com.learning.security_spring.domain.entity.Usuario;
import com.learning.security_spring.domain.entity.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, String> {

    @Query("""
            
            select distinct g.nome 
            from UsuarioGrupo ug
            join ug.grupo g
            join ug.usuario u
            where u = ?1
            
            """) //selecione os (distinct) diferentes  tipos de nome no UsuarioGrupo
    List<String> findPermissoesByUsuario(Usuario usuario);

}
