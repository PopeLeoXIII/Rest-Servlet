package org.example.repository;

import org.example.model.Reservation;
import org.example.model.ReservationToVehicle;
import org.example.model.Vehicle;

import java.util.List;

public interface ReservationToVehicleRepository {
    ReservationToVehicle save(ReservationToVehicle reservationToVehicle);

    List<Vehicle> getVehicleByReservationId(Long id);

    List<Reservation> getReservationByVehicleId(Long id);

    boolean deleteByData(ReservationToVehicle reservationToVehicle);
}
