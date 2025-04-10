package com.MyTutor2.service;


import com.MyTutor2.exceptions.ObjectNotFoundException;
import com.MyTutor2.model.DTOs.ExRatesDTO;

import java.math.BigDecimal;
import java.util.Optional;

//ExchangeRates_Step_5 define an interface with the needed abstract methods
public interface ExRateService {

    boolean hasInitialisedExRates();

    ExRatesDTO fetchExRates();

    void updateRates(ExRatesDTO exRatesDTO);

    Optional<BigDecimal> findExRate(String from, String to);

    BigDecimal convert(String from, String to, BigDecimal amount) throws ObjectNotFoundException;
}
