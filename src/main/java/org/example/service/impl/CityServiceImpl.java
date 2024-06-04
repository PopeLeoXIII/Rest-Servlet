package org.example.service.impl;

import org.example.NotFoundException;
import org.example.service.CityService;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

public class CityServiceImpl implements CityService {
    private static CityService instance;

    private CityServiceImpl () {
    }

    public static synchronized CityService getInstance() {
        if (instance == null) {
            instance = new CityServiceImpl();
        }
        return instance;
    }

    @Override
    public CityOutGoingDto save(CityIncomingDto role) {
        return null;
    }

    @Override
    public void update(CityUpdateDto role) throws NotFoundException {

    }

    @Override
    public CityOutGoingDto findById(Long roleId) throws NotFoundException {
        return null;
    }

    @Override
    public List<CityOutGoingDto> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long roleId) throws NotFoundException {
        return false;
    }
}
