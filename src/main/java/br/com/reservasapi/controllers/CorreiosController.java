package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.OpenCepResponse;
import br.com.reservasapi.services.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/correios")
@RequiredArgsConstructor
public class CorreiosController {

    private final ViaCepService viaCepService;

    @Operation(summary = "Consulta endereco pelo CEP", description = "Retorna os dados do endereço pelo CEP")
    @GetMapping("/cep/{cep}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<OpenCepResponse> buscarCep(@PathVariable String cep) {
        OpenCepResponse response = viaCepService.buscarCep(cep);

        if (response == null || response.getCep() == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
