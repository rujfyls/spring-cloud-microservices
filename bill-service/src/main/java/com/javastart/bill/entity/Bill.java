package com.javastart.bill.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long billId;

    @Column(name = "account_id")
    private Long accountId;

    private BigDecimal amount;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @Column(name = "overdraft_enabled")
    private Boolean overdraftEnabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bill bill = (Bill) o;
        return billId != null && Objects.equals(billId, bill.billId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
