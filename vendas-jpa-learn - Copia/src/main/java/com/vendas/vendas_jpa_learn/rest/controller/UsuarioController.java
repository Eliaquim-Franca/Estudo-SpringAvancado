package com.vendas.vendas_jpa_learn.rest.controller;

import com.vendas.vendas_jpa_learn.domain.entity.Usuario;
import com.vendas.vendas_jpa_learn.exception.SenhaInvalidaException;
import com.vendas.vendas_jpa_learn.rest.dto.CredenciaisDTO;
import com.vendas.vendas_jpa_learn.rest.dto.TokenDTO;
import com.vendas.vendas_jpa_learn.security.jwt.JwtService;
import com.vendas.vendas_jpa_learn.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String criptoSenha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(criptoSenha);
        return  service.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = service.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
