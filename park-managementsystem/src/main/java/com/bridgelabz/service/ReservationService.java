package com.bridgelabz.service;

import com.bridgelabz.dto.ReservationDTO;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.entity.User;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO cancelReservation(Long reservationId);
    List<ReservationDTO> getUserReservations(Long userId);

}

