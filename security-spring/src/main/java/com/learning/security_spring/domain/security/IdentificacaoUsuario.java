package com.learning.security_spring.domain.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentificacaoUsuario {

    private String id;
    private String nome;
    private String login;
    private List<String> permissoes;

    public List<String> getPermissoes(){

        if(permissoes ==null){
            return  new ArrayList<>();
        }
        return permissoes;
    }

}
