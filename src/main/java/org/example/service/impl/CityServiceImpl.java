package org.example.service.impl;

import org.example.NotFoundException;
import org.example.model.City;
import org.example.repository.CityRepository;
import org.example.repository.impl.CityRepositoryImpl;
import org.example.repository.mapper.CityMapper;
import org.example.repository.mapper.impl.CityMapperImpl;
import org.example.service.CityService;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

public class CityServiceImpl implements CityService {
    private CityRepository repository = CityRepositoryImpl.getInstance();
    private final CityMapper mapper = CityMapperImpl.getInstance();
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
    public CityOutGoingDto save(CityIncomingDto incomingDto) {
        City city = mapper.mapIncomingDto(incomingDto);
        city = repository.save(city);
        return mapper.mapModel(city);
    }

    @Override
    public void update(CityUpdateDto updateDto) throws NotFoundException {

    }

    @Override
    public CityOutGoingDto findById(Long id) throws NotFoundException {
        return null;
    }

    @Override
    public List<CityOutGoingDto> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long id) throws NotFoundException {
        return false;
    }
}
