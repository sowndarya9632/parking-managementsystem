package com.bridgelabz.dto;

import com.bridgelabz.entity.Bill.*;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private Long reservationId;
    private double amount;
    private PaymentStatus paymentStatus;


}

