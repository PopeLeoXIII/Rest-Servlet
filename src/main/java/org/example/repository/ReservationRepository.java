package org.example.repository;

import org.example.model.Reservation;
import org.example.model.User;
import org.example.model.Vehicle;

import java.util.List;

public interface ReservationRepository extends SimpleRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(User user);

//    List<Vehicle> getVehicleListByReservationId(Long id);
}
