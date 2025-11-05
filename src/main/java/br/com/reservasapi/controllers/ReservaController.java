package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.services.ReservaService;
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

    @GetMapping
    public ResponseEntity<List<ReservaDto>> listarTodas() {
        return ResponseEntity.ok().body(reservaService.listarTodas());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ReservaDto>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok().body(reservaService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservaService.buscarPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody ReservaDto dto) {
        ReservaDto reservaAtualizada = reservaService.atualizar(id, dto);
        return ResponseEntity.ok(reservaAtualizada);
    }

    @PostMapping
    public ResponseEntity<ReservaDto> cadastrar(@Valid @RequestBody ReservaDto dto) {
        ReservaDto novaReserva = reservaService.cadastrar(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
