package br.com.reservasapi.services;

import br.com.reservasapi.dto.OpenCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public OpenCepResponse  buscarCep(String cep){
        String url = "https://opencep.com/v1/" + cep;

        return restTemplate.getForObject(url, OpenCepResponse.class);
    }
}
