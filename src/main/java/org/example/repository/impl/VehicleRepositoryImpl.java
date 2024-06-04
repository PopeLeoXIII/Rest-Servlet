package org.example.repository.impl;

import org.example.model.Vehicle;
import org.example.repository.VehicleRepository;

import java.util.List;

public class VehicleRepositoryImpl implements VehicleRepository {
    private static VehicleRepository instance;
    private VehicleRepositoryImpl() {
    }

    public static synchronized VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Vehicle findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Vehicle findAll() {
        return null;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return null;
    }

    @Override
    public List<Vehicle> findAllByCityId(Long id) {
        return List.of();
    }
}
