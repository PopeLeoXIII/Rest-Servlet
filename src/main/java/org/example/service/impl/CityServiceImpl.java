package org.example.service.impl;

import org.example.repository.exception.NotFoundException;
import org.example.model.City;
import org.example.repository.CityRepository;
import org.example.repository.impl.CityRepositoryImpl;
import org.example.service.mapper.CityMapper;
import org.example.service.mapper.impl.CityMapperImpl;
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
        if (incomingDto == null || incomingDto.getName() == null || incomingDto.getName().isEmpty()) {
            throw new IllegalArgumentException ("Empty city name");
        }

        City city = mapper.mapIncomingDto(incomingDto);
        city = repository.save(city);
        return mapper.mapModel(city);
    }

    @Override
    public void update(CityUpdateDto updateDto) throws NotFoundException {
        if (updateDto == null || updateDto.getId() == null)
            throw new IllegalArgumentException("Empty city id");

        City city = mapper.mapUpdateDto(updateDto);
        repository.update(city);
    }

    @Override
    public CityOutGoingDto findById(Long id) throws NotFoundException {
        City city = repository.findById(id);
        return mapper.mapModel(city);
    }

    @Override
    public List<CityOutGoingDto> findAll() {
        List<City> cityList = repository.findAll();
        return mapper.mapModelList(cityList);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
