package org.example.repository;

import org.example.model.Reservation;
import org.example.model.ReservationToVehicle;
import org.example.model.Vehicle;

import java.util.List;

public interface ReservationToVehicleRepository extends SimpleRepository<ReservationToVehicle, Long> {
    List<Vehicle> findVehicleByReservationId(Long id);

    List<Reservation> getReservationByVehicleId(Long id);
}
