package br.com.reservasapi.services;

import br.com.reservasapi.dto.HospedeReservaRequest;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.model.HospedeReserva;
import br.com.reservasapi.model.Reserva;
import br.com.reservasapi.repositories.HospedeReservaRepository;
import br.com.reservasapi.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospedeReservaService {

    private final ReservaRepository reservaRepository;
    private final HospedeReservaRepository hospedeReservaRepository;

    public Reserva adicionarHospede(Long reservaId, HospedeReservaRequest request) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

        HospedeReserva hospede = new HospedeReserva();
        hospede.setNome(request.getNome());
        hospede.setDocumento(request.getDocumento());
        hospede.setReserva(reserva);

        hospedeReservaRepository.save(hospede);

        reserva.getHospedes().add(hospede);
        return reservaRepository.save(reserva);
    }
}
