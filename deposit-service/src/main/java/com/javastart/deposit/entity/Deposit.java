package com.javastart.deposit.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long depositId;

    private BigDecimal amount;

    @Column(name = "bill_id")
    private Long billId;

    @Column(name = "deposit_date")
    private OffsetDateTime depositDate;

    private String email;
}
