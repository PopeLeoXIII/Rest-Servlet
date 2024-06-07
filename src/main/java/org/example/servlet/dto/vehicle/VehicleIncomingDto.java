package org.example.servlet.dto.vehicle;

import org.example.model.City;
import org.example.model.Reservation;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

public class VehicleIncomingDto {
    private String name;
    private CityUpdateDto city;

    public VehicleIncomingDto() {}

    public VehicleIncomingDto(String name, CityUpdateDto city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public CityUpdateDto getCity() {
        return city;
    }
}
