package br.com.reservasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String documento;
}
