package br.com.reservasapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospede_reserva")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HospedeReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 20)
    private String documento;

    @ManyToOne
    @JoinColumn(
            name = "reserva_id0",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_hospede_reserva_reserva")
    )
    private Reserva reserva;
}
