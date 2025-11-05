package br.com.reservasapi.services;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.mapper.ReservaMapper;
import br.com.reservasapi.model.Reserva;
import br.com.reservasapi.repositories.ClienteRepository;
import br.com.reservasapi.repositories.QuartoRepository;
import br.com.reservasapi.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final QuartoRepository quartoRepository;
    private final ReservaMapper reservaMapper;

    public List<ReservaDto> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservaDto buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));
        return reservaMapper.toDto(reserva);
    }

    public ReservaDto salvar(ReservaDto dto) {

        var cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado!"));

        var quarto = quartoRepository.findById(dto.getQuartoId())
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));

        if (dto.getDataCheckIn() == null || dto.getDataCheckOut() == null) {
            throw new IllegalArgumentException("As datas de check-in e check-out são obrigatórias");
        }

        if (!dto.getDataCheckOut().isAfter(dto.getDataCheckIn())) {
            throw new IllegalArgumentException("A data de check-out deve ser posterior à data de check-in");
        }

        boolean quartoDisponivel = reservaRepository.existeReservaNoPeriodo(
                quarto.getId(), dto.getDataCheckIn(), dto.getDataCheckOut());
        if (quartoDisponivel) {
            throw new IllegalArgumentException("O quarto selecionado já está reservado nesse periodo");
        }

        long dias = ChronoUnit.DAYS.between(dto.getDataCheckIn(), dto.getDataCheckOut());
        if (dias <= 0) {
            throw new IllegalArgumentException("A reserva deve ser no minimo 1 dia de duração");
        }
        BigDecimal valorTotal = quarto.getPrecoDiaria().multiply(new BigDecimal(dias));
        dto.setValorTotal(valorTotal);

        dto.setStatus("ATIVA");
        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setCliente(cliente);
        reserva.setQuarto(quarto);
        Reserva reservaSalvo = reservaRepository.save(reserva);
        return reservaMapper.toDto(reservaSalvo);
    }

    public ReservaDto atualizar(Long id, ReservaDto reservaDto) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));

        var quarto = quartoRepository.findById(reservaDto.getQuartoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));

        boolean quartoIndisponivel = reservaRepository.existeReservaNoPeriodo(
                quarto.getId(), reservaDto.getDataCheckIn(), reservaDto.getDataCheckOut());

        if (quartoIndisponivel && !reservaExistente.getQuarto().getId().equals(reservaDto.getQuartoId())) {
            throw new IllegalArgumentException("O quarto selecionado já está reservado nesse periodo");
        }

        reservaExistente.setDataCheckIn(reservaDto.getDataCheckIn());
        reservaExistente.setDataCheckOut(reservaDto.getDataCheckOut());
        reservaExistente.setValorTotal(reservaDto.getValorTotal());
        reservaExistente.setStatus(reservaDto.getStatus());
        reservaExistente.setQuarto(quarto);

        long dias = ChronoUnit.DAYS.between(reservaDto.getDataCheckIn(), reservaDto.getDataCheckOut());
        reservaExistente.setValorTotal(quarto.getPrecoDiaria().multiply(new BigDecimal(dias)));

        return  reservaMapper.toDto(reservaRepository.save(reservaExistente));
    }

    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));
        reserva.setStatus("CANCELADA");
        reservaRepository.save(reserva);
    }
    
    public List<ReservaDto> listarPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId)
                .stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}
