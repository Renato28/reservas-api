package br.com.reservasapi.dto;

import lombok.Data;

@Data
public class EnderecoDto {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
