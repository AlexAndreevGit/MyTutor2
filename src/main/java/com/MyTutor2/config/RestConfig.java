package com.MyTutor2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

//ExchangeRates_Step_8
@Configuration
public class RestConfig {

    @Bean
    public RestClient restClient(){     //Spring -> https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
        return RestClient.create();
    }

}
