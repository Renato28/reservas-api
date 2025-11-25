package br.com.reservasapi.dto;

import br.com.reservasapi.enums.StatusQuarto;
import br.com.reservasapi.enums.TipoQuarto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuartoDto {

    private Long id;
    @NotNull(message = "O número do quarto é obrigatório")
    @Positive(message = "O numero do quarto deve ser positivo")
    private String numero;
    @NotBlank(message = "O tipo do quarto é obrigatório")
    private TipoQuarto tipo;
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private BigDecimal precoDiaria;
    private StatusQuarto status;
    private Long hotelId;
}
