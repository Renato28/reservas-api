package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.ReservaDto;
import br.com.reservasapi.dto.ReservaListagemDto;
import br.com.reservasapi.dto.ReservaResponseDto;
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

    @Mapping(source = "cliente", target = "cliente.nome")
    @Mapping(source = "quarto", target = "quarto.numero")
    ReservaListagemDto toListagemDto(Reserva reserva);

    @Mapping(target = "cliente", source = "cliente.nome")
    @Mapping(target = "quarto", source = "quarto.numero")
    ReservaResponseDto toResponseDto(Reserva reserva);

}
