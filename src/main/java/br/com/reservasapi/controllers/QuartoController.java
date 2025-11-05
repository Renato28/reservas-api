package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.QuartoDto;
import br.com.reservasapi.services.QuartoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quartoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDto> atualizar(@PathVariable Long id, @RequestBody QuartoDto dto) {
        QuartoDto quartoAtualizado = quartoService.atualizar(id, dto);
        return ResponseEntity.ok(quartoAtualizado);
    }

    @PostMapping
    public ResponseEntity<QuartoDto> cadastrar(@Valid @RequestBody QuartoDto dto) {
        QuartoDto quartoCriado = quartoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoCriado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
