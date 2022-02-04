package com.javastart.deposit.service;

import com.javastart.deposit.controller.dto.DepositResponseDTO;
import com.javastart.deposit.exception.DepositServiceException;
import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.AccountResponseDTO;
import com.javastart.deposit.rest.AccountServiceClient;
import com.javastart.deposit.rest.BillResponseDTO;
import com.javastart.deposit.rest.BillServiceClient;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void depositServiceTest_withBillId() {
        BillResponseDTO billResponseDTO = createBillResponseDTO();
        AccountResponseDTO accountResponseDTO = createAccountResponseDTO();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDTO);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(accountResponseDTO);
        DepositResponseDTO deposit = depositService.deposit(null, 1L, BigDecimal.valueOf(999L));
        Assertions.assertThat(deposit.getEmail()).isEqualTo(accountResponseDTO.getEmail());
    }

    @Test(expected = DepositServiceException.class)
    public void depositServiceTest_exception() {
        depositService.deposit(null, null, BigDecimal.valueOf(9L));
    }

    private BillResponseDTO createBillResponseDTO() {
        BillResponseDTO billResponseDTO = new BillResponseDTO();
        billResponseDTO.setAccountId(1L);
        billResponseDTO.setAmount(BigDecimal.valueOf(5000));
        billResponseDTO.setBillId(1L);
        billResponseDTO.setCreationDate(OffsetDateTime.now());
        billResponseDTO.setIsDefault(true);
        billResponseDTO.setOverdraftEnabled(true);

        return billResponseDTO;
    }

    private AccountResponseDTO createAccountResponseDTO() {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAccountId(1L);
        accountResponseDTO.setName("Alex");
        accountResponseDTO.setEmail("dexteraljp1@gmail.com");
        accountResponseDTO.setPhone("89524569148");
        accountResponseDTO.setCreationDate(OffsetDateTime.now());
        accountResponseDTO.setBills(List.of(1L, 2L, 3L));

        return accountResponseDTO;
    }
}
