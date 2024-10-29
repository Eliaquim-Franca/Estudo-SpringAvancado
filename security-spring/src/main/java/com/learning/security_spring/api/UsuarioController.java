package com.learning.security_spring.api;

import com.learning.security_spring.api.dro.CadastroUsuarioDTO;
import com.learning.security_spring.domain.entity.Usuario;
import com.learning.security_spring.domain.repository.UsuarioRepository;
import com.learning.security_spring.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRoler('ADMIN')")
    private ResponseEntity<Usuario> listar(@RequestBody CadastroUsuarioDTO body){
        Usuario usuarioSalvo = usuarioService.salvar(body.getUsuario(), body.getPermissoes());

        return ResponseEntity.ok(usuarioSalvo);
    }

}
