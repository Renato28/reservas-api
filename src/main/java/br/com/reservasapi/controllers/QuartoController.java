package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.QuartoDto;
import br.com.reservasapi.services.QuartoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService quartoService;

    @GetMapping
    public ResponseEntity<List<QuartoDto>> listarTodos() {
        return ResponseEntity.ok(quartoService.listarTodos());
    }

    @GetMapping("/{id]")
    public ResponseEntity<QuartoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quartoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDto> atualizar(@PathVariable Long id, @RequestBody QuartoDto dto) {
        return ResponseEntity.ok(quartoService.atualizar(id, dto));
    }

    @PostMapping
    public ResponseEntity<QuartoDto> salvar(@Valid @RequestBody QuartoDto dto) {
        return ResponseEntity.ok(quartoService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
