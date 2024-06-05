package org.example.servlet.dto.vehicle;

import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;
import org.example.servlet.dto.reservation.ReservationSmallOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public class VehicleUpdateDto {
    private Long id;
    private String name;

    private CityUpdateDto city;
    private List<ReservationUpdateDto> reservationList;

    public VehicleUpdateDto(Long id, String name, CityUpdateDto city, List<ReservationUpdateDto> reservationList) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.reservationList = reservationList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityUpdateDto getCity() {
        return city;
    }

    public List<ReservationUpdateDto> getReservationList() {
        return reservationList;
    }
}
