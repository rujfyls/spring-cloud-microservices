package com.javastart.account.service;

import com.javastart.account.exception.AccountNotFoundException;
import com.javastart.account.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test(expected = AccountNotFoundException.class)
    public void accountServiceTest_exception() {
        accountService.getAccountById(ArgumentMatchers.isNull());
    }

}
