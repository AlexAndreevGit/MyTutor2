package com.MyTutor2.init;


import com.MyTutor2.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//We need the component annotation so spring knows what to do with it
//CommandLineRunner is an interface from spring boot
//The commondLineRunner ist started once when the application is started
@Component
public class ExchangeRateInitializer implements CommandLineRunner {


    private final ExRateService exRateService;

    public ExchangeRateInitializer(ExRateService exRateService) {
        this.exRateService = exRateService;
    }


    @Override
    public void run(String... args) throws Exception {

        //if no initialised exchange rates then fetch the information through REST
        if (!exRateService.hasInitialisedExRates()){

            try {
                exRateService.updateRates(exRateService.fetchExRates());

            }catch (Exception e){
                System.out.println("Error during the retrieval of the exchange rates: " + e);
            }

        }
    }

}
