package br.com.reservasapi.services;

import br.com.reservasapi.dto.QuartoDto;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.mapper.QuartoMapper;
import br.com.reservasapi.model.Quarto;
import br.com.reservasapi.repositories.QuartoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final QuartoMapper quartoMapper;

    public List<QuartoDto> listarTodos() {
        return quartoRepository.findAll()
                .stream()
                .map(quartoMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuartoDto buscarPorId(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));
        return quartoMapper.toDto(quarto);
    }

    public QuartoDto cadastrar(QuartoDto dto) {
        Quarto quarto = quartoMapper.toEntity(dto);
        return quartoMapper.toDto(quartoRepository.save(quarto));
    }

    public String consultarStatus(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado!"));
        return quarto.getStatus().name();
    }

    public QuartoDto atualizar(Long id, QuartoDto dto) {
        Quarto quartoExistente = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));
        quartoExistente.setNumero(dto.getNumero());
        quartoExistente.setTipo(dto.getTipo());
        quartoExistente.setPrecoDiaria(dto.getPrecoDiaria());
        quartoExistente.setStatus(dto.getStatus());
        return quartoMapper.toDto(quartoRepository.save(quartoExistente));
    }

    public void deletar(Long id) {
        quartoRepository.deleteById(id);
    }
}
