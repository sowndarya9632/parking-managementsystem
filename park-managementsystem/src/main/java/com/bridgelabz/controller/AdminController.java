package com.bridgelabz.controller;

import com.bridgelabz.entity.Bill;
import com.bridgelabz.repository.BillRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private  BillRepository billRepository;

    public AdminController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @GetMapping("/revenue")
    public String getTotalRevenue() {
        List<Bill> bills = billRepository.findAll();
        Double totalRevenue = 0.0;
        for (Bill bill : bills) {
            totalRevenue += bill.getAmount();
        }
        return "Total Revenue: " + totalRevenue;
    }
}
