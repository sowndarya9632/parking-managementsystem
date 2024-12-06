package com.bridgelabz.service;

import com.bridgelabz.dto.ParkingSlotDTO;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface ParkingSlotService {
       ParkingSlotDTO addParkingSlot(ParkingSlotDTO parkingSlotDTO);
    HashMap<String, String> getStatus();
    public ParkingSlotDTO reserveParkingSlot(Long slotId, Long userId, VehicleType
            vehicleType, LocalDateTime startTime, LocalDateTime endTime);
    public ParkingSlotDTO releaseSlot(Long slotId);

    List<ParkingSlot> getSlotsByLevelAndVehicleType(Integer level, VehicleType vehicleType);

    List<ParkingSlot> getSlotsByLevel(Integer level);

    List<ParkingSlot> getSlotsByVehicleType(VehicleType vehicleType);
}
