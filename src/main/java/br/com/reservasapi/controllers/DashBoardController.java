package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.DashBoardResumoDto;
import br.com.reservasapi.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Resumo do dashboard das reservas", description = "retorna os dados do dashboard das reservas")
    public ResponseEntity<DashBoardResumoDto> resumo() {
        return ResponseEntity.ok(dashboardService.getResumo());
    }
}
