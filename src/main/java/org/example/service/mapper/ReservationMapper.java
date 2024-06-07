package org.example.service.mapper;

import org.example.model.Reservation;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationSmallOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public interface ReservationMapper extends TypedMapper<Reservation, ReservationIncomingDto,
        ReservationOutGoingDto, ReservationUpdateDto> {
    List<ReservationSmallOutGoingDto> mapModelListToSmallDto(List<Reservation> modelList);
    ReservationSmallOutGoingDto mapModelToSmallDto(Reservation model);

}
