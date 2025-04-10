package com.MyTutor2.init;


import com.MyTutor2.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//ExchangeRates_Step_9 update the exchange rates by teh start of the application
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
