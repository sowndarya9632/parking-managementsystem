package com.bridgelabz.service;

import com.bridgelabz.dto.BillDTO;
import com.bridgelabz.entity.Bill;
import com.bridgelabz.entity.Bill.*;
import com.bridgelabz.enums.PaymentStatus;
import com.bridgelabz.enums.VehicleType;


public interface BillService {
  BillDTO generateBill(Long reservationId);
 double  getRatePerHour(VehicleType vehicleType);
 BillDTO updateBillPaymentStatus(Long billId, PaymentStatus paymentStatus);
}

