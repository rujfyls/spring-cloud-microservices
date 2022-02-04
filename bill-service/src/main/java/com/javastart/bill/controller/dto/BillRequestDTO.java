package com.javastart.bill.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BillRequestDTO {

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Boolean overdraftEnabled;
}
