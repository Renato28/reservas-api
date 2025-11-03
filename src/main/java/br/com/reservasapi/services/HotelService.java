package br.com.reservasapi.services;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.mapper.HotelMapper;
import br.com.reservasapi.model.Hotel;
import br.com.reservasapi.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelDto> listarTodos() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    public HotelDto buscarPorId(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado"));
        return hotelMapper.toDto(hotel);
    }

    public HotelDto salvar(HotelDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        return hotelMapper.toDto(hotelRepository.save(hotel));
    }

    public HotelDto atualizar(Long id, HotelDto dto) {
        Hotel hotelExistente = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado"));
        hotelExistente.setNome(dto.getNome());
        hotelExistente.setEndereco(dto.getEndereco());
        hotelExistente.setCidade(dto.getCidade());
        hotelExistente.setEstado(dto.getEstado());
        hotelExistente.setTelefone(dto.getTelefone());
        return hotelMapper.toDto(hotelRepository.save(hotelExistente));
    }

    public void deletar(Long id) {
        hotelRepository.deleteById(id);
    }
}
