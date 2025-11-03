package br.com.reservasapi.services;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.mapper.ReservaMapper;
import br.com.reservasapi.model.Reserva;
import br.com.reservasapi.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public List<ReservaDto> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservaDto buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));
        return reservaMapper.toDto(reserva);
    }

    public ReservaDto salvar(Long id, ReservaDto reservaDto) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));
        reservaExistente.setDataCheckIn(reservaDto.getDataCheckIn());
        reservaExistente.setDataCheckOut(reservaDto.getDataCheckOut());
        reservaExistente.setValorTotal(reservaDto.getValorTotal());
        reservaExistente.setStatus(reservaDto.getStatus());
        return  reservaMapper.toDto(reservaRepository.save(reservaExistente));
    }

    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}
