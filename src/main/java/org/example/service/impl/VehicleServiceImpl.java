package org.example.service.impl;

import org.example.repository.exception.NotFoundException;
import org.example.model.Vehicle;
import org.example.repository.VehicleRepository;
import org.example.repository.impl.VehicleRepositoryImpl;
import org.example.service.mapper.VehicleMapper;
import org.example.service.mapper.impl.VehicleMapperImpl;
import org.example.service.VehicleService;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    private VehicleRepository repository = VehicleRepositoryImpl.getInstance();
    private final VehicleMapper mapper = VehicleMapperImpl.getInstance();
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
    public VehicleOutGoingDto save(VehicleIncomingDto incomingDto) {
        if (incomingDto == null || incomingDto.getName() == null || incomingDto.getName().isEmpty())
            throw new IllegalArgumentException ("Empty vehicle name");

        if (incomingDto.getCity() == null || incomingDto.getCity().getId() == null)
            throw new IllegalArgumentException ("Empty city id");

        Vehicle vehicle = mapper.mapIncomingDto(incomingDto);
        vehicle = repository.save(vehicle);
        return mapper.mapModel(vehicle);
    }

    @Override
    public void update(VehicleUpdateDto updateDto) throws NotFoundException {
        if (updateDto == null || updateDto.getId() == null)
            throw new IllegalArgumentException("Empty vehicle id");

        Vehicle vehicle = mapper.mapUpdateDto(updateDto);
        repository.update(vehicle);
    }

    @Override
    public VehicleOutGoingDto findById(Long id) throws NotFoundException {
        Vehicle vehicle = repository.findById(id);
        return mapper.mapModel(vehicle);
    }

    @Override
    public List<VehicleOutGoingDto> findAll() {
        List<Vehicle> vehicleList = repository.findAll();
        return mapper.mapModelList(vehicleList);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
