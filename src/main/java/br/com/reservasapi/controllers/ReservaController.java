package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Listar todas as reservas", description = "Retorna uma lista completa de reservas cadastradas")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<List<ReservaDto>> listarTodas() {
        return ResponseEntity.ok().body(reservaService.listarTodas());
    }

    @Operation(summary = "Lista as reservas pelo ID do cliente", description = "Retorna uma lista completa de reservas pelo cliente")
    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<List<ReservaDto>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok().body(reservaService.listarPorCliente(clienteId));
    }

    @Operation(summary = "Busca uma reserva pelo ID", description = "Retorna a reserva pelo ID informado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservaService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza os dados de uma reserva cadastrada pelo ID", description = "Retorna o status OK de reserva atualizada com sucesso")
    @PutMapping("{id}")
    @PreAuthorize("hasRole('RECEPCIONISTA')")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody ReservaDto dto) {
        ReservaDto reservaAtualizada = reservaService.atualizar(id, dto);
        return ResponseEntity.ok(reservaAtualizada);
    }

    @Operation(summary = "Cadastra uma nova reserva", description = "Retorna os dados da reserva cadastrada")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCIONISTA', 'HOSPEDE')")
    public ResponseEntity<ReservaDto> cadastrar(@Valid @RequestBody ReservaDto dto) {
        ReservaDto novaReserva = reservaService.cadastrar(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }


    @Operation(summary = "Realiza o check-in da reserva", description = "Retorna status 200 de check-in realizado com sucesso")
    @PutMapping("/check-in/{id}")
    @PreAuthorize("hasRole('RECEPCIONISTA')")
    public ResponseEntity<Void> realizarCheckIn(@PathVariable Long id){
        reservaService.realizarCheckIn(id);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Realiza o check-out da reserva", description = "Retorna status 200 de check-out realizado com sucesso")
    @PutMapping("/check-out/{id}")
    @PreAuthorize("hasRole('RECEPCIONISTA')")
    public ResponseEntity<Void> realizarCheckOut(@PathVariable Long id){
        reservaService.realizarCheckOut(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancela uma reserva pelo ID", description = "Retorna o status 204 de reserva cancelada com sucesso")
    @PatchMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'RECEPCIONISTA')")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Confirma uma reserva pelo ID", description = "Retorna os dados da reserva confirmada")
    @PatchMapping("/confirmar/{id}")
    public ResponseEntity<ReservaDto> confirmarReserva(@PathVariable Long id){
        var reserva = reservaService.confirmarReserva(id);
        return ResponseEntity.ok().body(reserva);
    }

    @Operation(summary = "Consulta o status da reserva", description = "Retorna o status da reserva")
    @GetMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIM', 'GERENTE', 'RECEPCIONISTA')")
    public ResponseEntity<String> consultarStatus(@PathVariable Long id){
        String status = reservaService.consultarStatus(id);
        return ResponseEntity.ok().body(status);
    }

    @Operation(summary = "Deleta os dados de uma reserva cadastrada pelo ID", description = "Retorna o status 204 de reserva deletada com sucesso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<?> verificarDisponibilidade(
            @RequestParam Long quartoId,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut){

     boolean disponivel = reservaService.verificarDisponibilidade(quartoId, checkIn, checkOut);

     return ResponseEntity.ok(Collections.singletonMap("disponivel", disponivel));

    }
}
