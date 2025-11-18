package br.com.reservasapi.services;

import br.com.reservasapi.exceptions.RegraDeNegocioException;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.model.PasswordResetToken;
import br.com.reservasapi.model.Usuario;
import br.com.reservasapi.repositories.PasswordResetTokenRepository;
import br.com.reservasapi.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        PasswordResetToken token = new PasswordResetToken();
        token.setUsuario(usuario);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiration(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(token);

        String link = "http://localhost:4200/reset-password?token=" + token.getToken();

        emailService.enviarEmail(
                usuario.getEmail(),
                "Recuperação de Senha",
                "Clique para redefinir sua senha: " + link);

    }

    public void redefinirSenha(String token, String novaSenha) {

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido"));

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Token expirado");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        passwordResetTokenRepository.delete(resetToken);
    }
}
