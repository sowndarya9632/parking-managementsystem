package com.bridgelabz.dto;

import com.bridgelabz.entity.Bill.*;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation.*;
import com.bridgelabz.entity.User;
import com.bridgelabz.enums.ReservationStatus;
import com.bridgelabz.enums.VehicleType;
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
public class ReservationDTO {

    private Long userId;
    private Long slotId;

    @Pattern(regexp = "^[A-Z0-9]{1,15}$", message = "valid vehicle registration number")
    private String vehicleNumber;

    @FutureOrPresent(message = "Start time must be in the future or present")
    private LocalDateTime startTime;
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    private VehicleType vehicleType;
    private ReservationStatus status;

}

