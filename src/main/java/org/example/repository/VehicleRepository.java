package org.example.repository;

import org.example.model.Vehicle;

import java.util.List;

public interface VehicleRepository extends SimpleRepository<Vehicle, Long> {
    List<Vehicle> findAllByCityId(Long id);
}

