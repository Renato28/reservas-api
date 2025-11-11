package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.UsuarioRequestDto;
import br.com.reservasapi.model.Usuario;
import br.com.reservasapi.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "usuarios", description = "Gerenciamento de cadastro de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Cadastra usuários", description = "Retorna os dados do usuario cadastrado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioRequestDto dto){
        Usuario usuarioCriado = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
}
