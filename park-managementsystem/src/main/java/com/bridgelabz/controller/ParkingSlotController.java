package com.bridgelabz.controller;
import com.bridgelabz.dto.ParkingSlotDTO;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.enums.VehicleType;
import com.bridgelabz.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @Autowired
    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }
    @GetMapping("/status")
    public Map<String ,String> getStatus() {
        return  parkingSlotService.getStatus();
    }

    @PostMapping("/reserve")
    public ParkingSlotDTO reserveParkingSlot(@RequestParam Long slotId, @RequestParam Long userId,
                                             @RequestParam VehicleType vehicleType,
                                             @RequestParam("startTime") String startTimeStr,
                                             @RequestParam("endTime") String endTimeStr) {
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);
        return parkingSlotService.reserveParkingSlot(slotId, userId, vehicleType, startTime, endTime);
    }

    // Endpoint to release a parking slot
    @PostMapping("/release/{slotId}")
    public ParkingSlotDTO releasSlot(@PathVariable Long slotId) {

        return parkingSlotService.releaseSlot(slotId);
    }
    @PostMapping("/addslot")
    public ParkingSlotDTO addParkingSlot(@RequestBody ParkingSlotDTO parkingSlotDTO) {
     return parkingSlotService.addParkingSlot(parkingSlotDTO);
    }
    @GetMapping("/filter")
    public List<ParkingSlot> filterSlots(
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) VehicleType vehicleType) {

        if (level != null && vehicleType != null) {
            return parkingSlotService.getSlotsByLevelAndVehicleType(level, vehicleType);
        } else if (level != null) {
            return parkingSlotService.getSlotsByLevel(level);
        } else if (vehicleType != null) {
            return parkingSlotService.getSlotsByVehicleType(vehicleType);
        }
        else
        {
            return null;
        }
    }
}

