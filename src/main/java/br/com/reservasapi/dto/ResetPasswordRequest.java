package br.com.reservasapi.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String novaSenha;
}
