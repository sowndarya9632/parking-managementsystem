package com.bridgelabz.entity;

import com.bridgelabz.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @OneToOne
    @JoinColumn(name = "reservation_id")
    @JsonIgnore
    private Reservation reservation;

}
