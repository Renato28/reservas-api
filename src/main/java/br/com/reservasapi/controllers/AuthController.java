package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.LoginRequest;
import br.com.reservasapi.dto.RegistroUsuarioRequest;
import br.com.reservasapi.model.Usuario;
import br.com.reservasapi.repositories.UsuarioRepository;
import br.com.reservasapi.security.UsuarioPrincipal;
import br.com.reservasapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Retorna o token e perfil do usuario logado")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();

        String token = authService.gerarToken(principal.getUsuario());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registra um novo usuário", description = "Retorna mensagem de usuario registrado com sucesso")
    public ResponseEntity<?> registrar(@RequestBody RegistroUsuarioRequest request) {
        Usuario usuario = authService.registrar(request);
        return ResponseEntity.ok("Usuário registrado com sucesso" + usuario.getEmail());
    }
}
