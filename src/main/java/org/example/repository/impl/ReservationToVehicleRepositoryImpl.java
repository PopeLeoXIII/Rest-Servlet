package org.example.repository.impl;

import org.example.model.Reservation;
import org.example.model.ReservationToVehicle;
import org.example.model.Vehicle;
import org.example.repository.ReservationToVehicleRepository;

import java.util.List;

public class ReservationToVehicleRepositoryImpl implements ReservationToVehicleRepository {
    private static ReservationToVehicleRepository instance;
    private ReservationToVehicleRepositoryImpl() {
    }

    public static synchronized ReservationToVehicleRepository getInstance() {
        if (instance == null) {
            instance = new ReservationToVehicleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public ReservationToVehicle findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public ReservationToVehicle findAll() {
        return null;
    }

    @Override
    public ReservationToVehicle save(ReservationToVehicle reservationToVehicle) {
        return null;
    }

    @Override
    public List<Vehicle> findVehicleByReservationId(Long id) {
        return List.of();
    }

    @Override
    public List<Reservation> getReservationByVehicleId(Long id) {
        return List.of();
    }
}
