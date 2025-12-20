package br.com.reservasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    @NotBlank(message = "O nome do hotel é obrigatório")
    private String nome;
    @Size(max = 30, message = "O telefone deve ter no máximo 30 caracteres")
    private String telefone;
    @NotNull(message = "O endereco é obrigatório")
    private EnderecoDto endereco;
}
