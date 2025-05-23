package com.MyTutor2.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//ExchangeRates_Step_4
@Configuration
@ConfigurationProperties(prefix = "forex.api")
public class ForexApiConfig {


    private String key;

    private String url;

    private String base;


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

    @PostConstruct
    public void checkConfiguration() {

        verifyNotNullOrEmpty("key", key);
        verifyNotNullOrEmpty("base", base);
        verifyNotNullOrEmpty("url", url);

        if (!"USD".equals(base)) {

            throw new IllegalStateException(" Sorry but the free API does not support base currencies different then USD.");

        }

    }

    private static void verifyNotNullOrEmpty(String name, String value) {
        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException("Property" + name + "cant be empty");
        }
    }

}
