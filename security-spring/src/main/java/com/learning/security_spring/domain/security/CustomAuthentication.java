package com.learning.security_spring.domain.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    private final IdentificacaoUsuario identificacaoUsuario;

    public CustomAuthentication(IdentificacaoUsuario identificacaoUsuario) {
        if(identificacaoUsuario == null){
            throw new ExceptionInInitializerError("Não foi possivel criar um custom Authentication sem a identificacao do usuario");
        }
        this.identificacaoUsuario = identificacaoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.identificacaoUsuario
                .getPermissoes()
                .stream()
                .map(perm-> new SimpleGrantedAuthority(perm)).toList();
    }

    @Override
    public Object getCredentials() {
        return null;//podemos retornar a senha se colocar true
    }

    @Override
    public Object getDetails() {
        return null;//podemos colocar os meta dados (hora de autenticacao, ip da maquina e afins)
    }

    @Override
    public Object getPrincipal() {
        return this.identificacaoUsuario; //É a identificacao do usuario
    }

    @Override
    public boolean isAuthenticated() {
        return true; // se colocar false nunca vamos conseguir logar esse autentication
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw  new IllegalArgumentException("Não precisa chamar estamos autenticados!!");
    }

    @Override
    public String getName() {
        return this.identificacaoUsuario.getNome();
    }
}
