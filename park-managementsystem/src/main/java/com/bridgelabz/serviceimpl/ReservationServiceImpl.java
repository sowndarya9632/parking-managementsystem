package com.bridgelabz.serviceimpl;
import com.bridgelabz.dto.ReservationDTO;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.entity.User;
import com.bridgelabz.enums.ReservationStatus;
import com.bridgelabz.exception.ReservationConflictException;
import com.bridgelabz.exception.SlotUnavailableException;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.repository.ParkingSlotRepository;
import com.bridgelabz.repository.ReservationRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ParkingSlotRepository parkingSlotRepository,
                                  UserRepository userRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ParkingSlot parkingSlot = parkingSlotRepository.findById(reservationDTO.getSlotId())
                .orElseThrow(() -> new SlotUnavailableException("Slot not found or unavailable"));

        if (!parkingSlot.getIsAvailable()) {
            throw new SlotUnavailableException("Slot is unavailable");
        }
        if (parkingSlot.getVehicleType()!=(reservationDTO.getVehicleType())) {
            System.out.println("Parking Slot Vehicle Type: " + parkingSlot.getVehicleType());
            System.out.println("Reservation DTO Vehicle Type: " + reservationDTO.getVehicleType());

            throw new SlotUnavailableException("Slot not suitable for the vehicle type");
        }

        boolean conflict = reservationRepository.existsByParkingSlotAndStartTimeLessThanAndEndTimeGreaterThan(
                parkingSlot, reservationDTO.getEndTime(), reservationDTO.getStartTime());
        if (conflict) {
            throw new ReservationConflictException("Slot timing conflicts with an existing reservation");
        }

        // Create the Reservation entity
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSlotId(reservationDTO.getSlotId());
        reservation.setVehicleNumber(reservationDTO.getVehicleNumber());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setVehicleType(reservationDTO.getVehicleType());

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Mark the parking slot as unavailable
        parkingSlot.setIsAvailable(false);
        parkingSlotRepository.save(parkingSlot);

        return mapToDTO(savedReservation);
    }
    @Override
    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new SlotUnavailableException("Reservation not found"));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new RuntimeException("Reservation is not active, cannot be canceled.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        ParkingSlot parkingSlot = parkingSlotRepository.findById(reservation.getSlotId())
                .orElseThrow(() -> new SlotUnavailableException("Slot not found"));

        parkingSlot.setIsAvailable(true);
        parkingSlotRepository.save(parkingSlot);

        return mapToDTO(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationDTO> getUserReservations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private List<ReservationDTO> mapToDTOList(List<Reservation> reservations) {
        return reservations.stream().map(this::mapToDTO).toList();
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(reservation.getUser().getId());
        reservationDTO.setSlotId(reservation.getSlotId());
        reservationDTO.setVehicleNumber(reservation.getVehicleNumber());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setVehicleType(reservation.getVehicleType());
        reservationDTO.setStatus(reservation.getStatus());
        return reservationDTO;
    }
}
