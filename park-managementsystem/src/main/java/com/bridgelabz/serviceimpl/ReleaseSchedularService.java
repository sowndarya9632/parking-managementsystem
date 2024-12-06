package com.bridgelabz.serviceimpl;

import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.enums.ReservationStatus;
import com.bridgelabz.exception.SlotUnavailableException;
import com.bridgelabz.repository.ParkingSlotRepository;
import com.bridgelabz.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class ReleaseSchedularService {
    private static ParkingSlotRepository parkingSlotRepository;
    private static ReservationRepository reservationRepository;

    public ReleaseSchedularService(ParkingSlotRepository parkingSlotRepository, ReservationRepository reservationRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.reservationRepository = reservationRepository;
    }
    @Scheduled(fixedRate = 60000) // This runs every minute
   static public void checkForNoShows() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Reservation> activeReservations = reservationRepository.findByStatus(ReservationStatus.ACTIVE);

        for (Reservation reservation : activeReservations) {
            LocalDateTime startTime = reservation.getStartTime();
            if (currentTime.isAfter(startTime.plusHours(1)) && reservation.getStatus() == ReservationStatus.ACTIVE) {
                reservation.setStatus(ReservationStatus.NO_SHOW);
                reservationRepository.save(reservation);
                ParkingSlot parkingSlot = parkingSlotRepository.findById(reservation.getSlotId())
                        .orElseThrow(() -> new SlotUnavailableException("Slot not found"));
                parkingSlot.setIsAvailable(true);
                parkingSlotRepository.save(parkingSlot);

                System.out.println("Reservation " + reservation.getReservationId() + " marked as No Show.");
            }
        }
    }
}

