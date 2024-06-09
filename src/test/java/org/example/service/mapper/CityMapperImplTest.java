package org.example.service.mapper;

import org.example.model.City;
import org.example.model.Vehicle;
import org.example.service.mapper.impl.CityMapperImpl;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class CityMapperImplTest {
    private static final CityMapper mapper = CityMapperImpl.getInstance();
    private static final Long expectedId = 1L;
    private static final String expectedName = "Санкт-Петербург";
    private static final Vehicle vehicle = new Vehicle(2L, "Автомобиль", null, List.of());
    private static final City expectedCity = new City(expectedId, expectedName, List.of(vehicle));

    @Test
    void testGetInstance() {
        CityMapper cityMapper = CityMapperImpl.getInstance();
        Assertions.assertNotNull(cityMapper);
    }

    @Test
    void mapIncomingDtoTest() {
        CityIncomingDto cityIncomingDto = new CityIncomingDto(expectedName);
        City city = mapper.mapIncomingDto(cityIncomingDto);
        Assertions.assertEquals(expectedName, city.getName());
    }

    @Test
    void mapUpdateDtoTest() {
        CityUpdateDto updateDto = new CityUpdateDto(expectedId, expectedName);
        City city = mapper.mapUpdateDto(updateDto);
        Assertions.assertEquals(expectedId, city.getId());
        Assertions.assertEquals(expectedName, city.getName());
    }

    @Test
    void mapModelTest() {
        CityOutGoingDto outgoingDto = mapper.mapModel(expectedCity);
        Assertions.assertEquals(expectedId, outgoingDto.getId());
        Assertions.assertEquals(expectedName, outgoingDto.getName());
        Assertions.assertEquals(expectedCity.getVehicleList().size(), outgoingDto.getVehicleList().size());
    }

    @Test
    void testMapModel() {
        CityOutGoingDto outgoingDto = mapper.mapModel(expectedCity);

        Assertions.assertEquals(expectedId, outgoingDto.getId());
        Assertions.assertEquals(expectedName, outgoingDto.getName());
        Assertions.assertNotNull(outgoingDto.getVehicleList());
    }

    @Test
    void testMapModelList() {
        List<City> modelList = Arrays.asList(
                new City(2L, "Санкт-Петербург", List.of(vehicle)),
                new City(3L, "Екатеринбург", List.of(vehicle)));

        List<CityOutGoingDto> outgoingList = mapper.mapModelList(modelList);

        Assertions.assertEquals(outgoingList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            Assertions.assertEquals(outgoingList.get(i).getId(), modelList.get(i).getId());
            Assertions.assertEquals(outgoingList.get(i).getName(), modelList.get(i).getName());
            Assertions.assertNotNull(outgoingList.get(i).getVehicleList());
        }

    }

}