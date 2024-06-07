package org.example.service.mapper;

import org.example.model.Vehicle;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public interface VehicleMapper extends TypedMapper<Vehicle, VehicleIncomingDto, VehicleOutGoingDto, VehicleUpdateDto> {

    List<VehiclePlaneDto> mapModelListToPlaneDto(List<Vehicle> modelList);

    VehiclePlaneDto mapModelToPlaneDto(Vehicle model);

    Vehicle mapPlaneDtoToVehicle(VehiclePlaneDto planeDto);

    List<Vehicle> mapPlaneDtoListToModel(List<VehiclePlaneDto> planeDtoList);
}
