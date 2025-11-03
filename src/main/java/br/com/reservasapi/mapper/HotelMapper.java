package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.HotelDto;
import br.com.reservasapi.model.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelDto toDto(Hotel hotel);
    Hotel toEntity(HotelDto hotelDto);
}
