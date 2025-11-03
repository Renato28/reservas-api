package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.ClienteDto;
import br.com.reservasapi.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDto toDto(Cliente cliente);
    Cliente toEntity(ClienteDto clienteDto);
}
