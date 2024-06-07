package org.example.repository;

import org.example.model.Reservation;
import org.example.model.ReservationToVehicle;
import org.example.model.Vehicle;

import java.util.List;

public interface ReservationToVehicleRepository {
    ReservationToVehicle save(ReservationToVehicle reservationToVehicle);

    List<Vehicle> findVehicleListByReservationId(Long id);

    List<Reservation> findReservationListByVehicleId(Long id);

    boolean deleteByData(ReservationToVehicle reservationToVehicle);
}
