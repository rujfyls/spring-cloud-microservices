package com.javastart.notification.service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResponseDTO {

    private BigDecimal amount;

    private String email;
}
