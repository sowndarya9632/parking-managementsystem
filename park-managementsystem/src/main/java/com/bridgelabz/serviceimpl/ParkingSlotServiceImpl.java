package com.bridgelabz.serviceimpl;
import com.bridgelabz.dto.ParkingSlotDTO;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.enums.VehicleType;
import com.bridgelabz.exception.SlotUnavailableException;
import com.bridgelabz.repository.ParkingSlotRepository;
import com.bridgelabz.repository.ReservationRepository;
import com.bridgelabz.service.ParkingSlotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ReservationRepository reservationRepository;

    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository, ReservationRepository reservationRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.reservationRepository = reservationRepository;
    }
    @Override
    public ParkingSlotDTO addParkingSlot(ParkingSlotDTO parkingSlotDTO) {
        ParkingSlot getParkingSlot = parkingSlotRepository.findBySlotNumber(parkingSlotDTO.getSlotNumber());
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(parkingSlotDTO.getSlotNumber());
        parkingSlot.setLevel(parkingSlotDTO.getLevel());
        parkingSlot.setVehicleType(parkingSlotDTO.getVehicleType());
        if(getParkingSlot==null) {
            parkingSlotRepository.save(parkingSlot);
            return parkingSlotDTO;
        }
        else
            throw new IllegalArgumentException("Slot number "+parkingSlot.getSlotNumber()+" already exists");
    }

    @Override
    public HashMap<String, String> getStatus() {
        HashMap<String, String> status = new HashMap<>();
        List<ParkingSlot> parkingSlots =parkingSlotRepository.findAll();
        parkingSlots.forEach(parkingSlot -> {status.put(parkingSlot.getSlotNumber(), parkingSlot.getIsAvailable()?"available":"occupied");});
        return status;
    }
    @Override
    public ParkingSlotDTO reserveParkingSlot(Long slotId, Long userId, VehicleType vehicleType, LocalDateTime startTime, LocalDateTime endTime) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId).orElseThrow(() -> new SlotUnavailableException("slot unavailable"));
        if (slot.getIsAvailable()) {
            Reservation reservation = new Reservation();
            reservation.setSlotId(slotId);
            reservation.setReservationId(userId);
            reservation.setVehicleType(vehicleType);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            slot.setIsAvailable(false);
            parkingSlotRepository.save(slot);
            reservationRepository.save(reservation);
        }
        return convertToDTO(slot);
    }

        @Override
    public ParkingSlotDTO releaseSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(()-> new SlotUnavailableException("slot not available"));
           slot.setIsAvailable(true);
            parkingSlotRepository.save(slot);
        return convertToDTO(slot);
    }

    private ParkingSlotDTO convertToDTO(ParkingSlot slot) {
        ParkingSlotDTO dto = new ParkingSlotDTO();
        dto.setSlotNumber(slot.getSlotNumber());
        dto.setLevel(slot.getLevel());
        dto.setIsAvailable(slot.getIsAvailable());
        dto.setVehicleType(slot.getVehicleType());
        return dto;
    }
    @Override
    public List<ParkingSlot> getSlotsByLevelAndVehicleType(Integer level, VehicleType vehicleType) {
        return parkingSlotRepository.findByLevelAndVehicleType(level, vehicleType);
    }

    @Override
    public List<ParkingSlot> getSlotsByLevel(Integer level) {
        return parkingSlotRepository.findByLevel(level);    }

    @Override
    public List<ParkingSlot> getSlotsByVehicleType(VehicleType vehicleType) {
        return (List<ParkingSlot>) parkingSlotRepository.findByVehicleType(vehicleType);
    }
}



