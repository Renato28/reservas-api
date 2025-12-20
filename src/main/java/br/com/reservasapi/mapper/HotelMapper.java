package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.dto.HotelResponseDto;
import br.com.reservasapi.model.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toEntity(HotelDto dto);

    HotelResponseDto toDto(Hotel hotel);
}

