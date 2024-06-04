package org.example.service.impl;

import org.example.NotFoundException;
import org.example.service.VehicleService;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    private static VehicleService instance;

    private VehicleServiceImpl() {
    }

    public static synchronized VehicleService getInstance() {
        if (instance == null) {
            instance = new VehicleServiceImpl();
        }
        return instance;
    }

    @Override
    public VehicleOutGoingDto save(VehicleIncomingDto role) {
        return null;
    }

    @Override
    public void update(VehicleUpdateDto role) throws NotFoundException {

    }

    @Override
    public VehicleOutGoingDto findById(Long roleId) throws NotFoundException {
        return null;
    }

    @Override
    public List<VehicleOutGoingDto> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long roleId) throws NotFoundException {
        return false;
    }
}
