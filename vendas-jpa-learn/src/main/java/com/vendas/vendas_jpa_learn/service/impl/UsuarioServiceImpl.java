package com.vendas.vendas_jpa_learn.service.impl;

import com.vendas.vendas_jpa_learn.domain.entity.Usuario;
import com.vendas.vendas_jpa_learn.domain.repositoriy.UsuarioRepository;
import com.vendas.vendas_jpa_learn.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service

public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasIguais = passwordEncoder.matches(usuario.getSenha(), user.getPassword());

        if(senhasIguais){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado na base de dados"));

        String[] roles = usuario.isAdmin()? new String[]{"ADMIN", "USER"}:new String[]{"USER"};

       return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).build();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
