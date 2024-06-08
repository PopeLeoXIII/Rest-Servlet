package org.example.service.mapper.impl;

import org.example.model.City;
import org.example.model.Reservation;
import org.example.model.Vehicle;
import org.example.service.mapper.CityMapper;
import org.example.service.mapper.ReservationMapper;
import org.example.service.mapper.VehicleMapper;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public class VehicleMapperImpl implements VehicleMapper {
    private static final CityMapper cityMapper = CityMapperImpl.getInstance();
    private static final ReservationMapper reservationMapper = ReservationMapperImpl.getInstance();

    private static VehicleMapper instance;

    private VehicleMapperImpl() {
    }

    public static synchronized VehicleMapper getInstance() {
        if (instance == null) {
            instance = new VehicleMapperImpl();
        }
        return instance;
    }

    @Override
    public Vehicle mapIncomingDto(VehicleIncomingDto vehicleIncomingDto) {
        City city = vehicleIncomingDto.getCity() != null ?
            cityMapper.mapUpdateDto(vehicleIncomingDto.getCity()) : null;

        return new Vehicle(
                null,
                vehicleIncomingDto.getName(),
                city,
                List.of()
        );
    }

    @Override
    public Vehicle mapUpdateDto(VehicleUpdateDto updateDto) {
        return new Vehicle(
                updateDto.getId(),
                updateDto.getName(),
                cityMapper.mapUpdateDto(updateDto.getCity()),
                List.of()
        );
    }

    @Override
    public VehicleOutGoingDto mapModel(Vehicle model) {
        if (model == null) {
            return new VehicleOutGoingDto();
        }

        return new VehicleOutGoingDto(
                model.getId(),
                model.getName(),
                cityMapper.mapModel(model.getCity()),
                reservationMapper.mapModelListToPlaneDto((model.getReservationList()))
        );
    }

    @Override
    public List<VehicleOutGoingDto> mapModelList(List<Vehicle> modelList) {
        return modelList.stream().map(this::mapModel).toList();
    }

    @Override
    public VehiclePlaneDto mapModelToPlaneDto(Vehicle model) {
        return new VehiclePlaneDto(
                model.getId(),
                model.getName()
        );
    }

    @Override
    public List<VehiclePlaneDto> mapModelListToPlaneDto(List<Vehicle> modelList) {
        return modelList.stream().map(this::mapModelToPlaneDto).toList();
    }

    @Override
    public Vehicle mapPlaneDtoToVehicle(VehiclePlaneDto planeDto) {
        return new Vehicle(
                planeDto.getId(),
                planeDto.getName(),
                null,
                List.of()
        );
    }

    @Override
    public List<Vehicle> mapPlaneDtoListToModel(List<VehiclePlaneDto> planeDtoList) {
        return planeDtoList.stream().map(this::mapPlaneDtoToVehicle).toList();
    }
}
