package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Listar todas as reservas", description = "Retorna uma lista completa de reservas cadastradas")
    @GetMapping
    public ResponseEntity<List<ReservaDto>> listarTodas() {
        return ResponseEntity.ok().body(reservaService.listarTodas());
    }

    @Operation(summary = "Lista as reservas pelo ID do cliente", description = "Retorna uma lista completa de reservas pelo cliente")
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ReservaDto>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok().body(reservaService.listarPorCliente(clienteId));
    }

    @Operation(summary = "Busca uma reserva pelo ID", description = "Retorna a reserva pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservaService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza os dados de uma reserva cadastrada pelo ID", description = "Retorna o status OK de reserva atualizada com sucesso")
    @PutMapping("{id}")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody ReservaDto dto) {
        ReservaDto reservaAtualizada = reservaService.atualizar(id, dto);
        return ResponseEntity.ok(reservaAtualizada);
    }

    @Operation(summary = "Cadastra uma nova reserva", description = "Retorna os dados da reserva cadastrada")
    @PostMapping
    public ResponseEntity<ReservaDto> cadastrar(@Valid @RequestBody ReservaDto dto) {
        ReservaDto novaReserva = reservaService.cadastrar(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @PutMapping("/check-in/{id}")
    public ResponseEntity<Void> realizarCheckIn(@PathVariable Long id){
        reservaService.realizarCheckIn(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/check-out/{id}")
    public ResponseEntity<Void> realizarCheckOut(@PathVariable Long id){
        reservaService.realizarCheckOut(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancela uma reserva pelo ID", description = "Retorna o status 204 de reserva cancelada com sucesso")
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta os dados de uma reserva cadastrada pelo ID", description = "Retorna o status 204 de reserva deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
