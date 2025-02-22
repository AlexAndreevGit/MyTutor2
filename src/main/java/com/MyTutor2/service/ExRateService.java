package com.MyTutor2.service;


import com.MyTutor2.model.DTOs.ExRatesDTO;

import java.math.BigDecimal;
import java.util.Optional;

//SpringSecurity_6 define an interface with the needed abstract methods
public interface ExRateService {

    boolean hasInitialisedExRates();

    ExRatesDTO fetchExRates();

    void updateRates(ExRatesDTO exRatesDTO);

    Optional<BigDecimal> findExRate(String from, String to);

    BigDecimal convert(String from, String to, BigDecimal amount);
}
