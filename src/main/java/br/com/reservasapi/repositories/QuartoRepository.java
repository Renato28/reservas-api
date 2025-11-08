package br.com.reservasapi.repositories;

import br.com.reservasapi.enums.StatusQuarto;
import br.com.reservasapi.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByHotelId(Long hotelId);
    List<Quarto> findByStatus(StatusQuarto status);
}
