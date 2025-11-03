package br.com.reservasapi.repositories;

import br.com.reservasapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByQuartoId(Long quartoId);
    List<Reserva> findByDataCheckInBetween(LocalDate inicio, LocalDate fim);
}