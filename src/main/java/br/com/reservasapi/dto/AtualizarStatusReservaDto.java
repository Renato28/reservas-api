package br.com.reservasapi.dto;

import br.com.reservasapi.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarStatusReservaDto {
    private StatusReserva statusReserva;
}
