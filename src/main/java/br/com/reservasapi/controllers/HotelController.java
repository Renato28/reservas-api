package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteis")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelDto>> listarTodos() {
        return ResponseEntity.ok(hotelService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HotelDto> cadastrar(@Valid @RequestBody HotelDto dto) {
        HotelDto hotelCriado = hotelService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> atualizar(@PathVariable Long id, @RequestBody HotelDto dto) {
        HotelDto hotelAtualizado = hotelService.atualizar(id, dto);
        return ResponseEntity.ok(hotelAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hotelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
