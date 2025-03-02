package com.MyTutor2.service.impl;


import com.MyTutor2.Exceptions.ObjectNotFoundException;
import com.MyTutor2.config.ForexApiConfig;
import com.MyTutor2.model.DTOs.ExRatesDTO;
import com.MyTutor2.model.entity.ExRateEntity;
import com.MyTutor2.repo.ExRateRepository;
import com.MyTutor2.service.ExRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

//ExchangeRates_Step_7 define all the needed methods

@Service
public class ExRateServiceImpl implements ExRateService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);  //initialise a logger to log messages

    private final ExRateRepository exRateRepository;
    private final RestClient restClient;
    private final ForexApiConfig forexApiConfig;

    public ExRateServiceImpl(ExRateRepository exRateRepository, RestClient restClient, ForexApiConfig forexApiConfig) {
        this.exRateRepository = exRateRepository;
        this.restClient = restClient;
        this.forexApiConfig = forexApiConfig;
    }

    @Override
    public boolean hasInitialisedExRates() {
        return exRateRepository.count() > 0;
    }

    //with the restClient we send a http query and we get a JSON response
    @Override
    public ExRatesDTO fetchExRates() {

        return restClient //with the help of the restClient we can send an HTTP request and get a response
                .get() //we want that the restClient make a GET request to the OpenExchangeRatesAPI
                .uri(forexApiConfig.getUrl(), forexApiConfig.getKey()) //make the GET request to this url. The {app_id} will be replaced with the kay. Spring knows that it should replace everything between {...}.
                .accept(MediaType.APPLICATION_JSON)// we tell to the website "OpenExchangeRates" that we want the response to be in a JSON format
                .retrieve() // we call the "retrieve()" method . Then step_1. in "educational->REST_Workflow" is happening and we wait for step_2.
                .body(ExRatesDTO.class); // return ExRatesDTO object. All exchange rate entities are in ExRatesDTO.


    }
    //Postman -> https://openexchangerates.org/api/latest.json?app_id=e1c2f30d79644bc798c585b49408fbfc&prettyprint=true&show_alternative=false

    @Override
    public void updateRates(ExRatesDTO exRatesDTO) {    //in the praxis  exRateService.updateRates(exRateService.fetchExRates());
        LOGGER.info("Updating {} rates.", exRatesDTO.rates().size());  //  "Updating 169 rates."

        //prove if the base is correct
        if (!forexApiConfig.getBase().equals(exRatesDTO.base())) {
            //Throw exception
            throw new IllegalArgumentException("the exchange rates that should be updated are not based on " + forexApiConfig.getBase() + "but rather on" + exRatesDTO.base());
        }

        //TODO understand and optimise
        exRatesDTO.rates().forEach((currency, rate) -> {   // go through the whole "currency, rate" map

            ExRateEntity exRateEntity = exRateRepository.findByCurrency(currency)   //do we have such an ExRateEntity
                    .orElseGet(() -> {    //If it doesnt exist in the DB, then create a new one
                                ExRateEntity exRateEntityNew = new ExRateEntity();
                                exRateEntityNew.setCurrency(currency);
                                return exRateEntityNew;
                            }
                    );

            //set the rate
            exRateEntity.setRate(rate);

            exRateRepository.save(exRateEntity);

        });

    }

    @Override
    public Optional<BigDecimal> findExRate(String from, String to) {

        if (Objects.equals(from, to)) {
            return Optional.of(BigDecimal.ONE);
        }

        //USD/BGN = x
        //USD/EUR = y

        //USD = x * BGN
        //USD = y * EUR

        //y * EUR = x * BGN

        //EUR/BGN = x / y

        Optional<BigDecimal> fromOpt = forexApiConfig.getBase().equals(from) ?
                Optional.of(BigDecimal.ONE) :
                exRateRepository.findByCurrency(from).map(ExRateEntity::getRate);

        Optional<BigDecimal> toOpt = forexApiConfig.getBase().equals(to) ?
                Optional.of(BigDecimal.ONE) :
                exRateRepository.findByCurrency(to).map(ExRateEntity::getRate);

        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(toOpt.get().divide(fromOpt.get(),RoundingMode.HALF_DOWN));
        }
    }

    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) {

        BigDecimal exchangeRate = findExRate(from, to)
                .orElseThrow(() -> new ObjectNotFoundException("Conversion from " + from + " to " + to + " not possible.")); //If the exchange rate is not found throw an exception

        return exchangeRate.multiply(amount);

    }

}
