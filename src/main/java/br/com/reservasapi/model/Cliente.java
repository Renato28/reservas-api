package br.com.reservasapi.model;

import br.com.reservasapi.enums.TipoCliente;
import br.com.reservasapi.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(length = 30)
    private String telefone;

    @Column(length = 20, unique = true)
    private String documento;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataAtualizacao = LocalDateTime.now();
}
