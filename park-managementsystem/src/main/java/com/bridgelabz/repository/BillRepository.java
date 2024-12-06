package com.bridgelabz.repository;

import com.bridgelabz.entity.Bill;
import com.bridgelabz.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    static boolean existsByReservation(Reservation reservation) {
        return false;
    }

    Bill findByReservation(Reservation reservation);

}

