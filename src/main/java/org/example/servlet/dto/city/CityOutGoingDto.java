package org.example.servlet.dto.city;

import org.example.servlet.dto.vehicle.VehiclePlaneDto;

import java.util.List;

public class CityOutGoingDto {
    private Long id;
    private String name;
    private List<VehiclePlaneDto> vehicleList;

    public CityOutGoingDto(){}

    public CityOutGoingDto(Long id, String name, List<VehiclePlaneDto> vehicleList) {
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

    public List<VehiclePlaneDto> getVehicleList() {
        return vehicleList;
    }
}
