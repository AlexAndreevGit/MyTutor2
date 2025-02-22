package com.MyTutor2.controller;


import com.MyTutor2.model.DTOs.CoversionResultDTO;
import com.MyTutor2.service.ExRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//SpringSecurity_9
@RestController   //@RestController means that the object that is returned will be converted to JSON object by spring
public class CurrencyController {

    private final ExRateService exRateService;

    public CurrencyController(ExRateService exRateService) {
        this.exRateService = exRateService;
    }

    @GetMapping("/api/convert")
    public ResponseEntity<CoversionResultDTO> convert(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("amount") BigDecimal amount) {

        BigDecimal result = exRateService.convert(from, to, amount);

        return ResponseEntity.ok(new CoversionResultDTO(from, to, amount, result));   // 1. Set the HTTP status to 200 OK and 2. allow Spring to serialize the DTO into a JSON response.

    }

    //1. LogIn in MyTutor
    //2. Send http://localhost:8080/api/convert?from=BGN&to=EUR&amount=10
    //3. The JSON result will be returned
    //4. activate Pretty-print

//    {
//            "from": "BGN",
//            "to": "EUR",
//            "amount": 10,
//            "result": 5.1
//    }

}
