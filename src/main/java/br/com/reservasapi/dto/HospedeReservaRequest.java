package br.com.reservasapi.dto;

import lombok.Data;

@Data
public class HospedeReservaRequest {
    private String nome;
    private String documento;
}
