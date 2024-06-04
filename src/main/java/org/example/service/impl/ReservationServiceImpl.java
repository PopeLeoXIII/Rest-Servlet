package org.example.service.impl;

import org.example.NotFoundException;
import org.example.service.CityService;
import org.example.service.ReservationService;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static ReservationService instance;

    private ReservationServiceImpl() {
    }

    public static synchronized ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationServiceImpl();
        }
        return instance;
    }

    @Override
    public ReservationOutGoingDto save(ReservationIncomingDto role) {
        return null;
    }

    @Override
    public void update(ReservationUpdateDto role) throws NotFoundException {

    }

    @Override
    public ReservationOutGoingDto findById(Long roleId) throws NotFoundException {
        return null;
    }

    @Override
    public List<ReservationOutGoingDto> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long roleId) throws NotFoundException {
        return false;
    }
}
