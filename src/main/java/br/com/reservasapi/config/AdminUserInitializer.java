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
        String gerenteEmail = "gerente@hotel.com";
        String recepcionistaEmail = "recepcionista@hotel.com";
        String camareiraEmail = "camareira@hotel.com";
        String hospedeEmail = "hospede@gmail.com";

        if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
            Usuario admin = Usuario.builder()
                    .nome("Renato Nóbrega")
                    .email(adminEmail)
                    .senha(passwordEncoder.encode("admin123"))
                    .perfil(Perfil.ADMIN)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(admin);

            Usuario gerente = Usuario.builder()
                    .nome("Pedro Souza")
                    .email(gerenteEmail)
                    .senha(passwordEncoder.encode("gerente123"))
                    .perfil(Perfil.GERENTE)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(gerente);

            Usuario recepcionista = Usuario.builder()
                    .nome("Eduarda Costa")
                    .email(recepcionistaEmail)
                    .senha(passwordEncoder.encode("recepcionista123"))
                    .perfil(Perfil.RECEPCIONISTA)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(recepcionista);

            Usuario camareira = Usuario.builder()
                    .nome("Maria da Silva")
                    .email(camareiraEmail)
                    .senha(passwordEncoder.encode("camareira123"))
                    .perfil(Perfil.CAMAREIRA)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(camareira);

            Usuario hospede = Usuario.builder()
                    .nome("João da Silva")
                    .email(hospedeEmail)
                    .senha(passwordEncoder.encode("hospede123"))
                    .perfil(Perfil.HOSPEDE)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
            usuarioRepository.save(hospede);

            System.out.println("Usuário ADMIN criado: " + adminEmail);
        } else {
            System.out.println("Usuário ADMIN já existe.");
        }
    }
}
