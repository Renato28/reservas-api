package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.HospedeReservaRequest;
import br.com.reservasapi.services.HospedeReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Hospedes", description = "Gerenciamento de hospedes da reserva")
public class HospedeReservaController {

    private final HospedeReservaService hospedeReservaService;

    @Operation(summary = "Adiciona um hospede a reserva", description = "Retorna os dados da reserva atualizada com hospede adicionar")
    @PostMapping("/{id}/hospedes")
    public ResponseEntity<?> adicionarHospede(
            @PathVariable Long id,
            @RequestBody HospedeReservaRequest request
            ) {
        var reservaAtualizada = hospedeReservaService.adicionarHospede(id, request);
        return ResponseEntity.ok(reservaAtualizada);
    }
}
