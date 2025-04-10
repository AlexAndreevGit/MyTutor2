package com.MyTutor2.model.DTOs;

import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Map;


//ExchangeRates_Step_3
public record ExRatesDTO(String base, Map<String, BigDecimal> rates) {

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
