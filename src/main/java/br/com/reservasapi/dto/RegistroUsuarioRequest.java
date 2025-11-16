package br.com.reservasapi.dto;

import lombok.Data;

@Data
public class RegistroUsuarioRequest {
    private String nome;
    private String email;
    private String senha;
}
