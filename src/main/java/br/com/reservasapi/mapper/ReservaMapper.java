package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.dto.ReservaListagemDto;
import br.com.reservasapi.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "quarto.id", target = "quartoId")
    ReservaDto toDto(Reserva reserva);

    @Mapping(source = "clienteId", target = "cliente.id")
    @Mapping(source = "quartoId", target = "quarto.id")
    Reserva toEntity(ReservaDto reservaDto);

    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "quarto.numero", target = "numeroQuarto")
    ReservaListagemDto toListagemDto(Reserva reserva);

}
