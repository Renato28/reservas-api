package br.com.reservasapi.config;

import br.com.reservasapi.enums.Perfil;
import br.com.reservasapi.model.Usuario;
import br.com.reservasapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@hotel.com";

        if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
            Usuario admin = Usuario.builder()
                    .email(adminEmail)
                    .senha(passwordEncoder.encode("admin123"))
                    .perfil(Perfil.ADMIN)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(admin);

            System.out.println("Usuário ADMIN criado: " + adminEmail);
        } else {
            System.out.println("Usuário ADMIN já existe.");
        }
    }
}
