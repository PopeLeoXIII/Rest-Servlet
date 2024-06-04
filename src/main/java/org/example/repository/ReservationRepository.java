package org.example.repository;

import org.example.model.Reservation;

import java.util.List;

public interface ReservationRepository extends SimpleRepository<Reservation, Long> {
    List<Reservation> findAllByUserId();
}
