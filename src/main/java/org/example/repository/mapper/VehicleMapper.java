package org.example.repository.mapper;

import org.example.model.Vehicle;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleSmallOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public interface VehicleMapper extends TypedMapper<Vehicle, VehicleIncomingDto, VehicleOutGoingDto, VehicleUpdateDto> {

    List<VehicleSmallOutGoingDto> mapModelListToSmallDto(List<Vehicle> modelList);

    VehicleSmallOutGoingDto mapModelToSmallDto(Vehicle model);
}
