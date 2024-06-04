package org.example.servlet.dto.city;

import org.example.servlet.dto.vehicle.VehicleSmallOutGoingDto;

import java.util.List;

public class CityOutGoingDto {
    private Long id;
    private String name;
    private List<VehicleSmallOutGoingDto> vehicleList;

    public CityOutGoingDto(){}

    public CityOutGoingDto(Long id, String name, List<VehicleSmallOutGoingDto> vehicleList) {
        this.id = id;
        this.name = name;
        this.vehicleList = vehicleList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<VehicleSmallOutGoingDto> getVehicleList() {
        return vehicleList;
    }
}
