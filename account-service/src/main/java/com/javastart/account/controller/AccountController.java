package com.javastart.account.controller;

import com.javastart.account.controller.dto.AccountRequestDTO;
import com.javastart.account.controller.dto.AccountResponseDTO;
import com.javastart.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.getAccountById(accountId)));
    }

    @PostMapping("/")
    public ResponseEntity<Long> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        return ResponseEntity.ok(accountService.createAccount(
                accountRequestDTO.getName(),
                accountRequestDTO.getEmail(),
                accountRequestDTO.getPhone(),
                accountRequestDTO.getBills()
        ));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId,
                                                            @RequestBody AccountRequestDTO accountRequestDTO) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.updateAccount(
                accountId,
                accountRequestDTO.getName(),
                accountRequestDTO.getEmail(),
                accountRequestDTO.getPhone(),
                accountRequestDTO.getBills()
        )));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> deleteAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(new AccountResponseDTO(accountService.deleteAccount(accountId)));
    }
}
