package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteis")
@RequiredArgsConstructor
@Tag(name = "Hoteis", description = "Gerenciamento de hoteis")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Listar todos os hoteis", description = "Retorna uma lista completa de hoteis cadastrados")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<List<HotelDto>> listarTodos() {
        return ResponseEntity.ok(hotelService.listarTodos());
    }

    @Operation(summary = "Busca um hotel pelo ID", description = "Retorna o hotel pelo ID informado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<HotelDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.buscarPorId(id));
    }

    @Operation(summary = "Cadastra um novo hotel", description = "Retorna os dados do hotel cadastrado")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelDto> cadastrar(@Valid @RequestBody HotelDto dto) {
        HotelDto hotelCriado = hotelService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelCriado);
    }

    @Operation(summary = "Atualiza os dados de um hotel cadastrado pelo ID", description = "Retorna o status OK de hotel atualizado com sucesso")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<HotelDto> atualizar(@PathVariable Long id, @RequestBody HotelDto dto) {
        HotelDto hotelAtualizado = hotelService.atualizar(id, dto);
        return ResponseEntity.ok(hotelAtualizado);
    }

    @Operation(summary = "Deleta os dados de um hotel cadastrado pelo ID", description = "Retorna o status 204 de hotel deletado com sucesso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hotelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
