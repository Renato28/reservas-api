package br.com.reservasapi.dto;

import br.com.reservasapi.enums.Status;
import br.com.reservasapi.validation.ValidReservaDatas;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidReservaDatas
public class ReservaDto {

    private Long id;
    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;
    @NotNull(message = "O quarto é obrigatório")
    private Long quartoId;
    @NotNull(message = "A data de check-in é obrigatória")
    @FutureOrPresent(message = "A data de check-in deve ser hoje ou no futuro")
    private LocalDate dataCheckIn;
    @NotNull(message = "A data de check-out é obrigatória")
    @FutureOrPresent(message = "A data de check-out deve ser hoje ou no futuro")
    private LocalDate dataCheckOut;
    private BigDecimal valorTotal;
    private Status status;
    private LocalDateTime dataCriacao;

}
