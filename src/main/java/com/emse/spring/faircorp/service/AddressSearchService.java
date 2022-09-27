package com.emse.spring.faircorp.service;

import com.emse.spring.faircorp.dto.ApiGouvAddressDto;
import com.emse.spring.faircorp.dto.ApiGouvFeatureDto;
import com.emse.spring.faircorp.dto.ApiGouvResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressSearchService {
    private final RestTemplate restTemplate;

    public AddressSearchService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.rootUri("https://api-adresse.data.gouv.fr/").build();
    }

    public List<ApiGouvAddressDto> getAddress(List<String> params){
        StringBuilder param = new StringBuilder();
        int count = 0;
        for (String item : params) {
            if (count == 0){
                param.append(item);
            }else{
                param.append("+").append(item);
            }
            count++;
        }
        String uri = UriComponentsBuilder.fromUriString("/search")
                .queryParam("q",param)
                .queryParam("limit",15)
                .build().toUriString();



        ApiGouvResponseDto responseDto = restTemplate.getForObject(uri, ApiGouvResponseDto.class);

        List<ApiGouvFeatureDto> featureDto = responseDto.getFeatures();

        List<ApiGouvAddressDto> apiGouvAddressDto = new ArrayList<>();
        for (ApiGouvFeatureDto item: featureDto) {
            apiGouvAddressDto.add(item.getProperties());
        }

        return apiGouvAddressDto;
    }
}
