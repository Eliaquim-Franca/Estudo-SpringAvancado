package com.learning.security_spring.domain.service;

import com.learning.security_spring.domain.entity.Grupo;
import com.learning.security_spring.domain.entity.Usuario;
import com.learning.security_spring.domain.entity.UsuarioGrupo;
import com.learning.security_spring.domain.repository.GrupoRepository;
import com.learning.security_spring.domain.repository.UsuarioGrupoRepository;
import com.learning.security_spring.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private  final GrupoRepository grupoRepository;
    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario, List<String> grupos){

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);

        List<UsuarioGrupo> listarUsuarioGrupo = grupos.stream().map(nomeGrupo -> {
            Optional<Grupo> possivelGrupo = grupoRepository.findByNome(nomeGrupo);
            if (possivelGrupo.isPresent()) {
                Grupo grupo = possivelGrupo.get();

                return new UsuarioGrupo(usuario, grupo);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        usuarioGrupoRepository.saveAll(listarUsuarioGrupo);

        return usuario;

    }

    public Usuario obterUsuarioComPermissoes(String login){

        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin(login);

        if(optionalUsuario.isEmpty()){
            return null;
        }

        Usuario usuario = optionalUsuario.get();

        List<String> permissoes = usuarioGrupoRepository.findPermissoesByUsuario(usuario);

        usuario.setPermissoes(permissoes);

        return usuario;
    }

}
