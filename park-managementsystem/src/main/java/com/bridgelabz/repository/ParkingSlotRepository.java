package com.bridgelabz.repository;

import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    ParkingSlot findBySlotNumber(String slotNumber);

    ParkingSlot findByVehicleType(VehicleType vehicleType);

    List<ParkingSlot> findByLevelAndVehicleType(int level, VehicleType vehicleType);

    List<ParkingSlot> findByLevel(int level);
}
