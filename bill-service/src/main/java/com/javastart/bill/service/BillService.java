package com.javastart.bill.service;

import com.javastart.bill.entity.Bill;
import com.javastart.bill.exception.BillNotFoundException;
import com.javastart.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId).orElseThrow(() ->
                new BillNotFoundException("Bill with id= " + billId + " not found!"));
    }

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setAccountId(accountId);
        bill.setCreationDate(OffsetDateTime.now());
        bill.setIsDefault(isDefault);
        bill.setOverdraftEnabled(overdraftEnabled);
        return billRepository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        Bill bill = getBillById(billId);
        bill.setAmount(amount);
        bill.setAccountId(accountId);
        bill.setIsDefault(isDefault);
        bill.setOverdraftEnabled(overdraftEnabled);
        return billRepository.save(bill);
    }

    public Bill deleteBillById(Long billId) {
        Bill bill = getBillById(billId);
        billRepository.delete(bill);
        return bill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return billRepository.getBillsByAccountId(accountId);
    }
}
