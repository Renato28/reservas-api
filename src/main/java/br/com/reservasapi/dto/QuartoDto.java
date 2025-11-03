package br.com.reservasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuartoDto {

    private Long id;
    private String numero;
    private String tipo;
    private BigDecimal precoDiaria;
    private Boolean disponivel;
    private Long hotelId;
}
