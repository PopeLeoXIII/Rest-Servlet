package org.example.service;

import org.example.NotFoundException;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

public interface CityService {
    CityOutGoingDto save(CityIncomingDto incomingDto);

    void update(CityUpdateDto updateDto) throws NotFoundException;

    CityOutGoingDto findById(Long id) throws NotFoundException;

    List<CityOutGoingDto> findAll();

    boolean delete(Long id) throws NotFoundException;
}
