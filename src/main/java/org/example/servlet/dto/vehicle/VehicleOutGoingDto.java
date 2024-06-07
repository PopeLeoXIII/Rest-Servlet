package org.example.servlet.dto.vehicle;

import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.reservation.ReservationPlaneDto;

import java.util.List;

public class VehicleOutGoingDto {
    private Long id;
    private String name;
    private CityOutGoingDto city;
    private List<ReservationPlaneDto> reservationList;

    public VehicleOutGoingDto() {}

    public VehicleOutGoingDto(Long id, String name, CityOutGoingDto city, List<ReservationPlaneDto> reservationList) {
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

    public CityOutGoingDto getCity() {
        return city;
    }

    public List<ReservationPlaneDto> getReservationList() {
        return reservationList;
    }
}
