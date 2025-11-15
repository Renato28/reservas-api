package br.com.reservasapi.repositories;

import br.com.reservasapi.model.HospedeReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospedeReservaRepository extends JpaRepository<HospedeReserva, Long> {
}
