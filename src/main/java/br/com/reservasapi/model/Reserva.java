package br.com.reservasapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_cliente"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "quarto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reserva_quarto"))
    private Quarto quarto;

    @Column(name = "data_check_in", nullable = false)
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out", nullable = false)
    private LocalDate dataCheckOut;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(length = 20)
    private String status = "ATIVA";

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
