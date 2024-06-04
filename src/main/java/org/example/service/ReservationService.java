package org.example.service;

import org.example.NotFoundException;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public interface ReservationService {
    ReservationOutGoingDto save(ReservationIncomingDto role);

    void update(ReservationUpdateDto role) throws NotFoundException;

    ReservationOutGoingDto findById(Long roleId) throws NotFoundException;

    List<ReservationOutGoingDto> findAll();

    boolean delete(Long roleId) throws NotFoundException;
}
