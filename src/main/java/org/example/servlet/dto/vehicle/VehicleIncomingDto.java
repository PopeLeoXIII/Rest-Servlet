package org.example.servlet.dto.vehicle;

import org.example.model.City;
import org.example.model.Reservation;
import org.example.servlet.dto.city.CityIncomingDto;

import java.util.List;

public class VehicleIncomingDto {
    private String name;

    public VehicleIncomingDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
