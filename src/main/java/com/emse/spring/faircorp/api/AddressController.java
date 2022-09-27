package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dto.ApiGouvAddressDto;
import com.emse.spring.faircorp.service.AddressSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
//@AllArgsConstructor
public class AddressController {

    private  final AddressSearchService addressSearchService;

    public AddressController(AddressSearchService addressSearchService){
        this.addressSearchService = addressSearchService;
    }

    @GetMapping()
    public List<ApiGouvAddressDto> findAll(@RequestParam String criteria){
        List<String> splitStr = List.of(criteria.trim().split("\\s+"));
        return addressSearchService.getAddress(splitStr);
    }
}
