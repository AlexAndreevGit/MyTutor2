package com.MyTutor2.model.DTOs;

import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Map;


//ExchangeRates_Step_3
//record is a class which primary purpose is to store immutable data
//a constructor public exRatesDTO(String base, Map<String, BigDecimal> rates) and getters are automatically generated
//Records are ideal when you need a simple data carrier without the need for boilerplate code.
public record ExRatesDTO(String base, Map<String, BigDecimal> rates) {

//Encapsulation of the JSON from OpenExchangeRates
//The result that is retrieved from OpenExchangeRates will be mapped to an ExRatesTDO object

//    @Override
//    public ExRatesDTO fetchExRates() {
//        return restClient
//                .get()
//                .uri(forexApiConfig.getUrl(), forexApiConfig.getKey())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .body(ExRatesDTO.class); // return ExRatesDTO object
//    }

}
