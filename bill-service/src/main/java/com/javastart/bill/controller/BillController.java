package com.javastart.bill.controller;

import com.javastart.bill.controller.dto.BillRequestDTO;
import com.javastart.bill.controller.dto.BillResponseDTO;
import com.javastart.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/bill") //это прописывать не надо так как в конфиг сервисе уже все прописал
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponseDTO> getBill(@PathVariable Long billId) {
        return ResponseEntity.ok(new BillResponseDTO(billService.getBillById(billId)));
    }

    @PostMapping("/")
    public ResponseEntity<Long> createBill(@RequestBody BillRequestDTO billRequestDTO) {
        return ResponseEntity.ok(billService.createBill(
                billRequestDTO.getAccountId(),
                billRequestDTO.getAmount(),
                billRequestDTO.getIsDefault(),
                billRequestDTO.getOverdraftEnabled()
        ));
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponseDTO> updateBill(@PathVariable Long billId,
                                                      @RequestBody BillRequestDTO billRequestDTO) {
        return ResponseEntity.ok(new BillResponseDTO(billService.updateBill(billId,
                billRequestDTO.getAccountId(),
                billRequestDTO.getAmount(),
                billRequestDTO.getIsDefault(),
                billRequestDTO.getOverdraftEnabled())));
    }

    @DeleteMapping("/billId")
    public ResponseEntity<BillResponseDTO> deleteBill(@PathVariable Long billId) {
        return ResponseEntity.ok(new BillResponseDTO(billService.deleteBillById(billId)));
    }
}
