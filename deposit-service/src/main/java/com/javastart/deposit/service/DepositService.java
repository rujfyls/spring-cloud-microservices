package com.javastart.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.deposit.controller.dto.DepositResponseDTO;
import com.javastart.deposit.entity.Deposit;
import com.javastart.deposit.exception.DepositServiceException;
import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate; //для отправки сообщений в сам RabbitMQ

    @Autowired
    public DepositService(DepositRepository depositRepository,
                          AccountServiceClient accountServiceClient,
                          BillServiceClient billServiceClient,
                          RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount) {
        if (billId == null && accountId == null) {
            throw new DepositServiceException("Account is null and Bill is null");
        }

        if (billId != null) {
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = createBillRequest(billResponseDTO);
            billRequestDTO.setAmount(billResponseDTO.getAmount().add(amount));

            billServiceClient.updateBill(billId, billRequestDTO);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDTO.getAccountId());

            Deposit deposit = new Deposit();
            deposit.setDepositDate(OffsetDateTime.now());
            deposit.setAmount(amount);
            deposit.setBillId(billId);
            deposit.setEmail(accountResponseDTO.getEmail());

            depositRepository.save(deposit);

            return getDepositResponse(amount, accountResponseDTO);
        }
        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequestDTO = createBillRequest(defaultBill);
        billRequestDTO.setAmount(defaultBill.getAmount().add(amount));

        billServiceClient.updateBill(defaultBill.getBillId(), billRequestDTO);
        AccountResponseDTO account = accountServiceClient.getAccountById(accountId);

        Deposit deposit = new Deposit();
        deposit.setDepositDate(OffsetDateTime.now());
        deposit.setAmount(amount);
        deposit.setBillId(defaultBill.getBillId());
        deposit.setEmail(account.getEmail());

        depositRepository.save(deposit);

        return getDepositResponse(amount, account);
    }

    private DepositResponseDTO getDepositResponse(BigDecimal amount, AccountResponseDTO account) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO();
        depositResponseDTO.setAmount(amount);
        depositResponseDTO.setEmail(account.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT,
                    ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDTO)); //преобразует объект в JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send message to RabbitMQ ");
        }

        return depositResponseDTO;
    }

    private BillResponseDTO getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDTO::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find default bill to account: " + accountId));
    }

    private BillRequestDTO createBillRequest(BillResponseDTO billResponseDTO) {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setAccountId(billResponseDTO.getAccountId());
        billRequestDTO.setCreationDate(billResponseDTO.getCreationDate());
        billRequestDTO.setIsDefault(billResponseDTO.getIsDefault());
        billRequestDTO.setOverdraftEnabled(billResponseDTO.getOverdraftEnabled());

        return billRequestDTO;
    }
}
