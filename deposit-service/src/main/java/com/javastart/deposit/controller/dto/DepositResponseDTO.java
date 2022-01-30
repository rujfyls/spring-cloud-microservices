package com.javastart.deposit.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResponseDTO {

    private BigDecimal amount;

    private String email;
}
