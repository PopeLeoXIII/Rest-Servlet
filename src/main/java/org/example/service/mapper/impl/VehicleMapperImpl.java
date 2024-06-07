package org.example.service.mapper.impl;

import org.example.model.City;
import org.example.model.Vehicle;
import org.example.service.mapper.CityMapper;
import org.example.service.mapper.ReservationMapper;
import org.example.service.mapper.VehicleMapper;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleSmallOutGoingDto;
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
                null
        );
    }

    @Override
    public Vehicle mapUpdateDto(VehicleUpdateDto vehicleUpdateDto) {
        return new Vehicle(
                vehicleUpdateDto.getId(),
                vehicleUpdateDto.getName(),
                cityMapper.mapUpdateDto(vehicleUpdateDto.getCity()),
                vehicleUpdateDto.getReservationList().stream().map(reservationMapper::mapUpdateDto).toList()
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
                reservationMapper.mapModelListToSmallDto((model.getReservationList()))
        );
    }

    @Override
    public List<VehicleOutGoingDto> mapModelList(List<Vehicle> modelList) {
        return modelList.stream().map(this::mapModel).toList();
    }

    @Override
    public VehicleSmallOutGoingDto mapModelToSmallDto(Vehicle model) {
        return new VehicleSmallOutGoingDto(
                model.getId(),
                model.getName()
        );
    }

    @Override
    public List<VehicleSmallOutGoingDto> mapModelListToSmallDto(List<Vehicle> modelList) {
        return modelList.stream().map(this::mapModelToSmallDto).toList();
    }
}
