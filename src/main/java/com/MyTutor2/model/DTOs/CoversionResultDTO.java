package com.MyTutor2.model.DTOs;

import java.math.BigDecimal;

public record CoversionResultDTO(String from, String to, BigDecimal amount, BigDecimal result) {

}
