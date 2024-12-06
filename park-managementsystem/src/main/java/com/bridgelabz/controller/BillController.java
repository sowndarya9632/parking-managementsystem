package com.bridgelabz.controller;
import com.bridgelabz.dto.BillDTO;
import com.bridgelabz.enums.PaymentStatus;
import com.bridgelabz.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PutMapping("/update/{billId}")
    public ResponseEntity<BillDTO> updateBillPaymentStatus(
            @PathVariable Long billId,
            @RequestBody PaymentStatus paymentStatus) {
        System.out.println("PaymentStatus received: " + paymentStatus);
        BillDTO billDTO = billService.updateBillPaymentStatus(billId, paymentStatus);
        return new ResponseEntity<>(billDTO, HttpStatus.OK);
    }

    @PostMapping("/generate/{reservationId}")
    public ResponseEntity<BillDTO> generateBill(@PathVariable Long reservationId){
        return new ResponseEntity<>(billService.generateBill(reservationId),HttpStatus.CREATED);
    }

}
