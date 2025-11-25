package br.com.reservasapi.model;

import br.com.reservasapi.enums.StatusQuarto;
import br.com.reservasapi.enums.TipoQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "quarto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipo;

    @Column(name = "preco_diaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoDiaria;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusQuarto status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quarto_hotel"))
    private Hotel hotel;
}
