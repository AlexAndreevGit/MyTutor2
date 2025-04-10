package com.MyTutor2.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

//ExchangeRates_Step_4 Defining ExRateEntity

@Entity
@Table(name= "ex_rates")
public class ExRateEntity extends BaseEntity{

    @Column(unique = true)
    private String currency;

    @Positive
    @NotNull
    private BigDecimal rate;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }


    @Override
    public String toString() {
        return "ExRateEntity{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }

}
