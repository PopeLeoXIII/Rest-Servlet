package org.example.service.mapper.impl;

import org.example.model.City;
import org.example.service.mapper.CityMapper;
import org.example.service.mapper.VehicleMapper;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

public class CityMapperImpl implements CityMapper {
    private static final VehicleMapper vehicleMapper = VehicleMapperImpl.getInstance();
    private static CityMapper instance;

    private CityMapperImpl() {
    }

    public static synchronized CityMapper getInstance() {
        if (instance == null) {
            instance = new CityMapperImpl();
        }
        return instance;
    }

    @Override
    public City mapIncomingDto(CityIncomingDto cityIncomingDto) {
        return new City(
                null,
                cityIncomingDto.getName(),
                null
        );
    }

    @Override
    public City mapUpdateDto(CityUpdateDto updateDto) {
        return new City(
                updateDto.getId(),
                updateDto.getName(),
                null
        );
    }

    @Override
    public CityOutGoingDto mapModel(City model) {
        if (model == null) {
            return new CityOutGoingDto();
        }

        return new CityOutGoingDto(
                model.getId(),
                model.getName(),
                vehicleMapper.mapModelListToSmallDto(model.getVehicleList())
        );
    }

    @Override
    public List<CityOutGoingDto> mapModelList(List<City> modelList) {
        return modelList.stream().map(this::mapModel).toList();
    }
}
