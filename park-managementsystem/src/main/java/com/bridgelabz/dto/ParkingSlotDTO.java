package com.bridgelabz.dto;

import com.bridgelabz.entity.ParkingSlot.*;
import com.bridgelabz.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParkingSlotDTO {

    private String slotNumber;
    private Integer level;
    private Boolean isAvailable;
    private VehicleType vehicleType;
}

