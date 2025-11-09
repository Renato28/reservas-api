package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.LoginRequest;
import br.com.reservasapi.dto.LoginResponse;
import br.com.reservasapi.repositories.UsuarioRepository;
import br.com.reservasapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken
                (request.getUsername(), request.getPassword());
        var usuario = authService.autenticar(authentication);
        String token = authService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token, usuario.getRole()));
    }
}
