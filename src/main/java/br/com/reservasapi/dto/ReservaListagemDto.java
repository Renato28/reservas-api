package br.com.reservasapi.dto;

import br.com.reservasapi.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaListagemDto {

    private Long id;
    private String nomeCliente;
    private String numeroQuarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private StatusReserva status;
    private BigDecimal valorTotal;
}
