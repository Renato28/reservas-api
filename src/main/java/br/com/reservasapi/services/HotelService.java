package br.com.reservasapi.services;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.dto.HotelResponseDto;
import br.com.reservasapi.dto.OpenCepResponse;
import br.com.reservasapi.exceptions.RegraDeNegocioException;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.mapper.EnderecoMapper;
import br.com.reservasapi.mapper.HotelMapper;
import br.com.reservasapi.model.Endereco;
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
    private final EnderecoMapper enderecoMapper;
    private final ViaCepService viaCepService;

    public List<HotelResponseDto> listarTodos() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    public HotelResponseDto buscarPorId(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));
        return hotelMapper.toDto(hotel);
    }

    public HotelResponseDto cadastrar(HotelDto dto) {
        OpenCepResponse response = viaCepService.buscarCep(dto.getEndereco().getCep());

        if (response == null || response.getCep() == null) {
            throw new RegraDeNegocioException("CEP inválido");
        }

        Endereco endereco = enderecoMapper.fromViaCep(response);

        Hotel hotel = hotelMapper.toEntity(dto);

        hotel.setEndereco(endereco);

        Hotel hotelSalvo = hotelRepository.save(hotel);

        return hotelMapper.toDto(hotelSalvo);
    }

    public HotelResponseDto atualizar(Long id, HotelDto dto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));
        hotel.setNome(dto.getNome());
        hotel.setTelefone(dto.getTelefone());

        Endereco endereco = hotel.getEndereco();
        endereco.setCep(dto.getEndereco().getCep());
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setLocalidade(dto.getEndereco().getLocalidade());
        endereco.setUf(dto.getEndereco().getUf());

        Hotel atualizado = hotelRepository.save(hotel);

        return hotelMapper.toDto(atualizado);

    }

    public void deletar(Long id) {
        hotelRepository.deleteById(id);
    }
}
