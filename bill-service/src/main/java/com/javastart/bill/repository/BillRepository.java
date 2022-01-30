package com.javastart.bill.repository;

import com.javastart.bill.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> getBillsByAccountId(Long accountId);
}
