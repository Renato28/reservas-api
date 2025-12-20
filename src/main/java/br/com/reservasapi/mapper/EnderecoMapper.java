package br.com.reservasapi.mapper;

import br.com.reservasapi.dto.OpenCepResponse;
import br.com.reservasapi.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id", ignore = true)
    Endereco fromViaCep(OpenCepResponse response);
}
