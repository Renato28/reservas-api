package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ClienteDto;
import br.com.reservasapi.services.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista completa de clientes cadastrados")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<List<ClienteDto>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(summary = "Busca um cliente pelo ID", description = "Retorna o cliente pelo ID informado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @GetMapping("/{nome}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA', 'CAMAREIRA', 'HOSPEDE')")
    @Operation(summary = "Busca um cliente pelo nome", description = "Retorna o cliente pelo nome informado")
    public ResponseEntity<ClienteDto> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(clienteService.buscarPorNome(nome));
    }

    @Operation(summary = "Cadastra um novo cliente", description = "Retorna os dados do cliente cadastrado")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA')")
    public ResponseEntity<ClienteDto> cadastrar(@Valid @RequestBody ClienteDto dto) {
        ClienteDto clienteCriado = clienteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }


    @Operation(summary = "Atualiza os dados de um cliente cadastrado pelo ID", description = "Retorna o status OK de cliente atualizado com sucesso")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'RECEPCIONISTA')")
    public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @RequestBody ClienteDto dto) {
        ClienteDto clienteAtualizado = clienteService.atualizar(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @Operation(summary = "Deleta os dados de um cliente cadastrado pelo ID", description = "Retorna o status 204 de cliente deletado com sucesso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
