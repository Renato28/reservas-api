package br.com.reservasapi.services;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.enums.StatusQuarto;
import br.com.reservasapi.enums.StatusReserva;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.mapper.ReservaMapper;
import br.com.reservasapi.model.Quarto;
import br.com.reservasapi.model.Reserva;
import br.com.reservasapi.repositories.ClienteRepository;
import br.com.reservasapi.repositories.QuartoRepository;
import br.com.reservasapi.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public ReservaDto cadastrar(ReservaDto dto) {

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

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setCliente(cliente);
        reserva.setQuarto(quarto);
        reserva.setStatus(StatusReserva.PENDENTE);
        reserva.setDataCriacao(LocalDateTime.now());
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

    public void realizarCheckIn(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

        // Verifica se já foi feito o check-in
        if (reserva.getStatus() == StatusReserva.EM_ANDAMENTO) {
            throw new IllegalStateException("O check-in já foi realizado!");
        }

        // verifica se a reserva está confirmada
        if (reserva.getStatus() == StatusReserva.CONFIRMADA) {
            throw new IllegalStateException("Somente reservas confirmadas podem realizar check-in!");
        }

        // Verifica se a data atual é válida para check-in
        LocalDate agora = LocalDate.now();
        if (agora.isBefore(reserva.getDataCheckIn())) {
            throw new IllegalStateException("Não é possivel realizar check-in antes da data prevista!");
        }


        // Atualiza status da reserva e data real de check-in
        reserva.setStatus(StatusReserva.EM_ANDAMENTO);
        reserva.setDataCheckIn(agora);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(StatusQuarto.OCUPADO);

        quartoRepository.save(quarto);
        reservaRepository.save(reserva);
    }

    public void realizarCheckOut(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
        if (reserva.getStatus() != StatusReserva.EM_ANDAMENTO) {
            throw new IllegalStateException("Somente reservas em andamento pode realizar check-out!");
        }

        LocalDate agora = LocalDate.now();

        if (agora.isBefore(reserva.getDataCheckIn())) {
            throw new IllegalStateException("Não é possivel realizar check-out antes do check-in!");
        }

        reserva.setStatus(StatusReserva.CONCLUIDA);
        reserva.setDataCheckOut(agora);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(StatusQuarto.DISPONIVEL);

        quartoRepository.save(quarto);
        reservaRepository.save(reserva);
    }

    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));

        // verifica se já está cancelada
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new IllegalArgumentException("A reservas já foi cancelada anteriormente");
        }

        // verifica se já fez check-in (opcional, caso queira impedir cancelamento após entrada)
        if (reserva.getStatus() == StatusReserva.EM_ANDAMENTO) {
            throw new IllegalArgumentException("Não é possível cancelar uma reserva que já iniciou ou foi concluida");
        }

        // verifica a data de check-in e calcular diferença em horas
        LocalDate agora = LocalDate.now();
        LocalDate dataCheckIn = reserva.getDataCheckIn();

        long diasRestantes = Duration.between(agora, dataCheckIn).toDays();

        if (diasRestantes < 2) {
            throw new IllegalArgumentException("A reserva só pode ser cancelada até dois dias antes da data do check-in");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    public ReservaDto confirmarReserva(Long reservaId) {

        var reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));

        if (reserva.getStatus() != StatusReserva.PENDENTE) {
            throw new IllegalArgumentException("Somente reservas pendentes podem ser confirmadas");
        }

        reserva.setStatus(StatusReserva.CONFIRMADA);

        var reservaSalva = reservaRepository.save(reserva);

        return reservaMapper.toDto(reservaSalva);
    }

    public String consultarStatus(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada!"));
        return reserva.getStatus().name();
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

    public boolean verificarDisponibilidade(Long quartoId, LocalDate dataCheckIn, LocalDate dataCheckOut) {

        if (dataCheckIn == null || dataCheckOut == null) {
            throw new IllegalArgumentException("As datas de check-in e check-out são obrigatórias");
        }

        if (!dataCheckOut.isAfter(dataCheckIn)) {
            throw new IllegalArgumentException("A data de check-out deve ser posterior à data de check-in");
        }

        quartoRepository.findById(quartoId)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado!"));

        boolean existeReserva = reservaRepository.existeReservaNoPeriodo(quartoId, dataCheckIn, dataCheckOut);
        
        return !existeReserva;
    }
}
