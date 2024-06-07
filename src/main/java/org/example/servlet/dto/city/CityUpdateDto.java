package org.example.servlet.dto.city;

import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public class CityUpdateDto {
    private Long id;
    private String name;
//    private List<VehicleUpdateDto> vehicleList;

    public CityUpdateDto(){}

    public CityUpdateDto(Long id, String name) { //, List<VehicleUpdateDto> vehicleList) {
        this.id = id;
        this.name = name;
//        this.vehicleList = vehicleList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public List<VehicleUpdateDto> getVehicleList() {
//        return vehicleList;
//    }
}
