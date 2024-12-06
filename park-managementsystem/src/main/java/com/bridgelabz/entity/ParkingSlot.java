package com.bridgelabz.entity;

import com.bridgelabz.dto.ParkingSlotDTO;
import com.bridgelabz.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String slotNumber;
    private Integer level;
    private Boolean isAvailable=true;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

}

