package com.bridgelabz.serviceimpl;
import com.bridgelabz.dto.BillDTO;
import com.bridgelabz.entity.Bill;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.enums.PaymentStatus;
import com.bridgelabz.enums.VehicleType;
import com.bridgelabz.exception.BillNotFoundException;
import com.bridgelabz.repository.BillRepository;
import com.bridgelabz.repository.ReservationRepository;
import com.bridgelabz.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillService {

    @Autowired
    private BillRepository billingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public BillDTO generateBill(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        if(BillRepository.existsByReservation(reservation)){
            throw new IllegalArgumentException("Bill already exists for this reservation");
        }
        long durationInHours = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toHours();
        double ratePerHour = getRatePerHour(reservation.getVehicleType());
        double totalAmount = ratePerHour * durationInHours;
        Bill billing = new Bill();
        billing.setReservation(reservation);
        billing.setAmount(totalAmount);
        billing.setPaymentStatus(PaymentStatus.valueOf("UNPAID"));
        Bill savedBilling = billingRepository.save(billing);
        return mapToDTO(savedBilling);
    }

    public double getRatePerHour(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:
                return 1.0;
            case CAR:
                return 3.0;
            case TRUCK:
                return 5.0;
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }
    @Override
    public BillDTO updateBillPaymentStatus(Long billId, PaymentStatus paymentStatus) {
        Bill bill = billingRepository.findById(billId).orElseThrow(()-> new BillNotFoundException("bill not found"));
            bill.setPaymentStatus(paymentStatus);
            billingRepository.save(bill);
      return mapToDTO(bill);
    }

    private BillDTO mapToDTO(Bill bill) {
        BillDTO billingDTO = new BillDTO();
        billingDTO.setReservationId(bill.getReservation().getReservationId());
        billingDTO.setAmount(bill.getAmount());
        billingDTO.setPaymentStatus(bill.getPaymentStatus());
        return billingDTO;
    }
}
