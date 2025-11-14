package br.com.reservasapi.services;

import br.com.reservasapi.dto.ClienteDto;
import br.com.reservasapi.exceptions.ResourceNotFoundException;
import br.com.reservasapi.mapper.ClienteMapper;
import br.com.reservasapi.model.Cliente;
import br.com.reservasapi.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public List<ClienteDto> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClienteDto buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toDto(cliente);
    }

    public ClienteDto buscarPorNome(String nome) {
        Cliente cliente = clienteRepository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toDto(cliente);
    }

    public ClienteDto cadastrar(ClienteDto dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    public ClienteDto atualizar(Long id, ClienteDto dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        clienteExistente.setNome(dto.getNome());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setTelefone(dto.getTelefone());
        clienteExistente.setDocumento(dto.getDocumento());
        return clienteMapper.toDto(clienteRepository.save(clienteExistente));
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
