package br.com.reservasapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 30)
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_hotel_endereco"))
    private Endereco endereco;
}

