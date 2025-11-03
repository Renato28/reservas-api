package br.com.reservasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    private Long id;
    private String nome;
    private String endereco;
    private String cidade;
    private String estado;
    private String telefone;
}
