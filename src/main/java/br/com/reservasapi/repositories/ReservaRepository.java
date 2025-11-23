package br.com.reservasapi.repositories;

import br.com.reservasapi.enums.StatusReserva;
import br.com.reservasapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    Long countByStatusIn(List<StatusReserva> status);

    Long countByStatus(StatusReserva status);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.dataCheckIn = :data")
    Long countByDataCheckIn(@Param("data") LocalDate data);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.dataCheckOut = :data")
    Long countByDataCheckOut(@Param("data") LocalDate data);

    @Query("SELECT COUNT(r.quarto.id) FROM Reserva r " +
            "WHERE r.status IN ('CONFIRMADA', 'CHECK_IN')")
    Long countQuartosOcupados();

    @Query("SELECT SUM(r.valorTotal) FROM Reserva r " +
            "WHERE MONTH(r.dataCriacao) = :mes AND YEAR(r.dataCriacao) = :ano " +
            "AND r.status IN ('CONFIRMADA', 'CHECK_OUT')")
    BigDecimal sumValorTotalByMes(int mes, int ano);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.dataCheckIn <= :hoje AND r.dataCheckOut >= :hoje")
    Long countHospedesAtuais(@Param("hoje") LocalDate hoje);

    @Query("SELECT COUNT(r) FROM Reserva r")
    Long countTotalReservas(); // Para calcular taxa de ocupação
}