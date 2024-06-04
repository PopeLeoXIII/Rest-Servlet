package org.example.repository.impl;

import org.example.model.Reservation;
import org.example.repository.ReservationRepository;
import org.example.repository.VehicleRepository;

import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {
    private static ReservationRepository instance;
    private ReservationRepositoryImpl() {
    }

    public static synchronized ReservationRepository getInstance() {
        if (instance == null) {
            instance = new ReservationRepositoryImpl();
        }
        return instance;
    }


    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Reservation findAll() {
        return null;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findAllByUserId() {
        return List.of();
    }
}
