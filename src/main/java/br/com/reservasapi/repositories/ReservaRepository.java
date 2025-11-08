package br.com.reservasapi.repositories;

import br.com.reservasapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByQuartoId(Long quartoId);
    List<Reserva> findByDataCheckInBetween(LocalDate inicio, LocalDate fim);

    @Query("""
        SELECT COUNT(r) > 0 FROM Reserva r 
        WHERE r.quarto.id = :quartoId
        AND r.dataCheckIn < :dataCheckOut
        AND r.dataCheckOut > :dataCheckIn            
    """)
    boolean existeReservaNoPeriodo(
            @Param("quartoId") Long quartoId,
            @Param("dataCheckIn") LocalDate dataCheckIn,
            @Param("dataCheckOut") LocalDate dataCheckOut
    );

    Long id(Long id);
}