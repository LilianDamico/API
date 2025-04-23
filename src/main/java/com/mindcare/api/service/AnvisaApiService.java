package com.mindcare.api.service;

import com.mindcare.api.dto.MedicamentoInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AnvisaApiService {

    public MedicamentoInfoDTO consultar(String nomeMedicamento) {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("bulario-api.vercel.app")
                .path("/api/medicamentos/" + nomeMedicamento)
                .build()
                .toUriString();

        try {
            return restTemplate.getForObject(url, MedicamentoInfoDTO.class);
        } catch (Exception e) {
            System.out.println("Erro ao consultar a API de medicamentos: " + e.getMessage());
            return null;
        }
    }
}
