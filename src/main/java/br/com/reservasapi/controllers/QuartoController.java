package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.QuartoDto;
import br.com.reservasapi.services.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService quartoService;

    @Operation(summary = "Listar todos os quartos", description = "Retorna uma lista completa de quartos cadastrados")
    @GetMapping
    public ResponseEntity<List<QuartoDto>> listarTodos() {
        return ResponseEntity.ok(quartoService.listarTodos());
    }

    @Operation(summary = "Busca um quarto pelo ID", description = "Retorna o quarto pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<QuartoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quartoService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza os dados de um quarto cadastrado pelo ID", description = "Retorna o status OK de quarto atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<QuartoDto> atualizar(@PathVariable Long id, @RequestBody QuartoDto dto) {
        QuartoDto quartoAtualizado = quartoService.atualizar(id, dto);
        return ResponseEntity.ok(quartoAtualizado);
    }

    @Operation(summary = "Cadastra um novo quarto", description = "Retorna os dados do quarto cadastrado")
    @PostMapping
    public ResponseEntity<QuartoDto> cadastrar(@Valid @RequestBody QuartoDto dto) {
        QuartoDto quartoCriado = quartoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoCriado);
    }

    @Operation(summary = "Consulta o status do quarto", description = "Retorna o status do quarto")
    @GetMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIM', 'GERENTE', 'RECEPCIONISTA')")
    public ResponseEntity<String> consultarStatus(@PathVariable Long id) {
        String status = quartoService.consultarStatus(id);
        return ResponseEntity.ok().body(status);
    }

    @Operation(summary = "Deleta os dados de um quarto cadastrado pelo ID", description = "Retorna o status 204 de quarto deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quartoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
