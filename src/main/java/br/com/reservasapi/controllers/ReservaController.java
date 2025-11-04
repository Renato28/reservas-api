package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.services.ReservaService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservaService.buscarPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody ReservaDto dto) {
        return ResponseEntity.ok(reservaService.atualizar(id, dto));
    }

    @PostMapping
    public ResponseEntity<ReservaDto> salvar(@RequestBody ReservaDto dto) {
        return  ResponseEntity.ok().body(reservaService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
