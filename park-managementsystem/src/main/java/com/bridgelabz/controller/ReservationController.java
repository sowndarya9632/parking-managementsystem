package com.bridgelabz.controller;
import com.bridgelabz.dto.ReservationDTO;
import com.bridgelabz.entity.ParkingSlot;
import com.bridgelabz.entity.Reservation;
import com.bridgelabz.entity.User;
import com.bridgelabz.exception.ReservationConflictException;
import com.bridgelabz.exception.UserNotFoundException;
import com.bridgelabz.service.ReservationService;
import com.bridgelabz.serviceimpl.ReleaseSchedularService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        ReleaseSchedularService.checkForNoShows();
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
            return new ResponseEntity<>( reservationService.cancelReservation(reservationId),HttpStatus.NOT_FOUND); // Reservation not found
        }


    // Endpoint to get all reservations for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getUserReservations(@PathVariable Long userId) {
        return new ResponseEntity<>(reservationService.getUserReservations(userId), HttpStatus.OK); // Return list of reservations with 200 status
    }
}

