package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.LoginRequest;
import br.com.reservasapi.dto.LoginResponse;
import br.com.reservasapi.repositories.UsuarioRepository;
import br.com.reservasapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "autenticação", description = "Autenticação de Usuários")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Retorna o token e role do usuario logado")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        var usuario = authService.autenticar(request.getUsername(), request.getPassword());

        String token = authService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token, usuario.getRole()));
    }
}
