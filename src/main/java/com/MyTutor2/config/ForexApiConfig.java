package com.MyTutor2.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//ExchangeRates_Step_5 Create the class ForexApiConfig to encapsulate the properties from the application.yaml in an object that we can use

//POJO for the configuration. Object with fields, getters and setters
//the fields should be with the same names as in the application.yaml
@Configuration
@ConfigurationProperties(prefix = "forex.api") // In the prefix we put forex.api from the application.yaml. Then we put "key", "url" and "base" as fields
public class ForexApiConfig {

    private String key;

    private String url;

    private String base;

    //Getter and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @PostConstruct//Control if key, base and url are configured correctly otherwise the application will not start
    public void checkConfiguration(){

        verifyNotNullOrEmpty("key",key);
        verifyNotNullOrEmpty("base",base);
        verifyNotNullOrEmpty("url",url);

        if(!"USD".equals(base)){

            //throw Exception
            throw new IllegalStateException(" Sorry but the free API does not support base currencies different then USD.");

        }

    }

    private static void verifyNotNullOrEmpty(String name, String value){
        if(value==null || value.isBlank()){

            //throw Exception
            throw new IllegalArgumentException("Property" + name + "cant be empty");
        }
    }

}
