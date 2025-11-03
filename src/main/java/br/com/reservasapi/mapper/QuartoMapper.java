package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.QuartoDto;
import br.com.reservasapi.model.Quarto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    QuartoDto toDto(Quarto quarto);

    @Mapping(source = "hotelId", target = "hotel.id")
    Quarto toEntity(QuartoDto quartoDto);
}
