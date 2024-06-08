package org.example.servlet.dto.vehicle;

import org.example.servlet.dto.city.CityUpdateDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public class VehicleUpdateDto {
    private Long id;
    private String name;

    private CityUpdateDto city;

    public VehicleUpdateDto() {}

    public VehicleUpdateDto(Long id, String name, CityUpdateDto city) {
        this.id = id;
        this.name = name;
        this.city = city;
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
}
