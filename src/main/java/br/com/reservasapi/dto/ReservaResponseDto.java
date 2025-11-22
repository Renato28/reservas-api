package br.com.reservasapi.dto;

import br.com.reservasapi.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDto {
    private Long id;
    private String cliente;
    private String quarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private BigDecimal valorTotal;
    private StatusReserva status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
