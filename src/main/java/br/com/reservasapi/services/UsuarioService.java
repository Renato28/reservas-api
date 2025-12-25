package br.com.reservasapi.services;

import br.com.reservasapi.dto.UsuarioRequestDto;
import br.com.reservasapi.exceptions.RegraDeNegocioException;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.model.Usuario;
import br.com.reservasapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioRequestDto dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraDeNegocioException("Já existe um usuario cadastrado com esse e-mail");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfil(dto.getPerfil())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
        return usuarioRepository.save(usuario);
    }

    public void ativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));
        usuario.ativar();
        usuarioRepository.save(usuario);
    }

    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));
        usuario.inativar();
        usuarioRepository.save(usuario);
    }
}
