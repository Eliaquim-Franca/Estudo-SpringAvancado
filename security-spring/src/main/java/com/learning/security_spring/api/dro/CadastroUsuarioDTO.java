package com.learning.security_spring.api.dro;

import com.learning.security_spring.domain.entity.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class CadastroUsuarioDTO {
    private Usuario usuario;
    private List<String> permissoes;
}
