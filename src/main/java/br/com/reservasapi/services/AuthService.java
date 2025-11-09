package br.com.reservasapi.services;

import br.com.reservasapi.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public Usuario autenticar(Authentication authentication) {
        Authentication auth = authenticationManager.authenticate(authentication);
        return (Usuario) auth.getPrincipal();
    }

    public String gerarToken(Usuario usuario) throws Exception {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("reservas-api")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(usuario.getEmail())
                .claim("role", usuario.getRole())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
