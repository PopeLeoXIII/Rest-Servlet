package org.example.servlet.dto.vehicle;

import org.example.model.City;
import org.example.model.Reservation;

import java.util.List;

public class VehicleSmallOutGoingDto {
    private Long id;
    private String name;

    public VehicleSmallOutGoingDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
