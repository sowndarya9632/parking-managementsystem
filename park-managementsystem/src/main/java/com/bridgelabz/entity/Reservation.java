package com.bridgelabz.entity;

import com.bridgelabz.enums.ReservationStatus;
import com.bridgelabz.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(insertable=true, updatable=true)
    private Long slotId;

    private String vehicleNumber;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToOne(mappedBy = "reservation")
    private Bill bill;

    @ManyToOne
    @JsonIgnore
    private ParkingSlot parkingSlot;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;


}
