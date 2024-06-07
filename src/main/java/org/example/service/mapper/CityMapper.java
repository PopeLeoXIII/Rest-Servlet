package org.example.service.mapper;

import org.example.model.City;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

public interface CityMapper extends TypedMapper<City, CityIncomingDto, CityOutGoingDto, CityUpdateDto> {
}
