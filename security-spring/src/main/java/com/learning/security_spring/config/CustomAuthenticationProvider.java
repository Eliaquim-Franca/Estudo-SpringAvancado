package com.learning.security_spring.config;

import com.learning.security_spring.domain.entity.Usuario;
import com.learning.security_spring.domain.security.CustomAuthentication;
import com.learning.security_spring.domain.security.IdentificacaoUsuario;
import com.learning.security_spring.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String nome = authentication.getName();
        String senha = (String) authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioComPermissoes(nome);

        if(usuario != null){
            boolean senhasBatem = passwordEncoder.matches(senha, usuario.getSenha());

            if (senhasBatem) {
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getPermissoes()
                );

                return new CustomAuthentication(identificacaoUsuario);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);// aqui estamos verificando se o authentication é uma instancia de UsernamePasswordAuthenticationToken
    }
}
