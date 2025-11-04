package br.com.reservasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    private Long id;
    @NotBlank(message = "O nome do hotel é obrigatório")
    private String nome;
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    private String endereco;
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;
    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres")
    private String estado;
    @Size(max = 30, message = "O telefone deve ter no máximo 30 caracteres")
    private String telefone;
}
