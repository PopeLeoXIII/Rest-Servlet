package org.example.service;

import org.example.NotFoundException;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public interface VehicleService {
    VehicleOutGoingDto save(VehicleIncomingDto role);

    void update(VehicleUpdateDto role) throws NotFoundException;

    VehicleOutGoingDto findById(Long roleId) throws NotFoundException;

    List<VehicleOutGoingDto> findAll();

    boolean delete(Long roleId) throws NotFoundException;
}
