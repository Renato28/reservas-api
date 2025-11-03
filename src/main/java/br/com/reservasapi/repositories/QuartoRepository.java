package br.com.reservasapi.repositories;

import br.com.reservasapi.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    List<Quarto> findByHotelId(Long hotelId);
    List<Quarto> findByDisponivelTrue();
}
