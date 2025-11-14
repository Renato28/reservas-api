package br.com.reservasapi.dto;

import br.com.reservasapi.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDto {

    @NotBlank(message = "O e-mail do usuário é obrigatório")
    @Email(message = "Formato inválido")
    private String email;
    @NotBlank(message = "A senha do usuário é obrigatória")
    private String senha;
    @NotBlank(message = "O perfil do usuário é obrigatória")
    private Perfil perfil;
}
