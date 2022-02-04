package com.javastart.account.controller.dto;

import com.javastart.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    private Long accountId;

    private String name;

    private String email;

    private String phone;

    private List<Long> bills;

    private OffsetDateTime creationDate;

    public AccountResponseDTO(Account account) {
        this.accountId = account.getAccountId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.bills = account.getBills();
        this.creationDate = account.getCreationDate();
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = OffsetDateTime.parse(creationDate);
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
