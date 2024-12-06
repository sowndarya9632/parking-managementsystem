package com.bridgelabz.repository;

import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.entity.User;
import com.bridgelabz.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByVehicleNumberAndStatus(String vehicleNumber, String active);
    List<Reservation> findByUserId(Long userId);

    Reservation findByVehicleNumber(String vehicleNumber);

    boolean existsByParkingSlotAndStartTimeLessThanAndEndTimeGreaterThan(ParkingSlot parkingSlot, LocalDateTime endTime, LocalDateTime startTime);

    List<Reservation> findByUser(User user);

    List<Reservation> findByStatus(ReservationStatus reservationStatus);
}
