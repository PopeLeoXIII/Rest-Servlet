package org.example.service.impl;

import org.example.repository.exception.NotFoundException;
import org.example.model.City;
import org.example.model.Vehicle;
import org.example.repository.CityRepository;
import org.example.repository.impl.CityRepositoryImpl;
import org.example.service.CityService;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class CityServiceImplTest {
    private final List<Vehicle> expectedVehicles = List.of();
    private final City expectedCity = new City(1L, "name", expectedVehicles);

    private static MockedStatic<CityRepositoryImpl> mokStaticRepo;
    private static final CityRepository mokRepoInstance = mock(CityRepository.class);

    @BeforeEach
    public void setUp() {
        mokStaticRepo = mockStatic(CityRepositoryImpl.class);
    }

    @AfterEach
    public void tearDown() {
        mokStaticRepo.close();
    }

    @Test
    void saveTest() {
        Mockito.doReturn(expectedCity).when(mokRepoInstance).save(Mockito.any(City.class));
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        CityService service = CityServiceImpl.getInstance();

        CityIncomingDto incomingDto = new CityIncomingDto(expectedCity.getName());
        CityOutGoingDto outGoingDto = service.save(incomingDto);

        Assertions.assertEquals(expectedCity.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedCity.getName(), outGoingDto.getName());
    }

    @Test
    void saveErrorTest() {
        CityService service = CityServiceImpl.getInstance();

        CityIncomingDto incomingDto = new CityIncomingDto("");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty city name", exception.getMessage());
    }

    @Test
    void findByIdTest() throws NotFoundException {
        Mockito.doReturn(expectedCity).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        CityService service = CityServiceImpl.getInstance();
        CityOutGoingDto outGoingDto = service.findById(expectedCity.getId());

        Assertions.assertEquals(expectedCity.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedCity.getName(), outGoingDto.getName());
    }

    @Test
    void findByIdErrorTest() throws NotFoundException {
        Mockito.doThrow(NotFoundException.class).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        CityService service = CityServiceImpl.getInstance();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.findById(expectedCity.getId());
        });
    }

    @Test
    void findAllTest() {
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);
        CityService service = CityServiceImpl.getInstance();
        service.findAll();

        Mockito.verify(mokRepoInstance).findAll();
    }

    @Test
    void updateTest() throws NotFoundException {
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        CityService service = CityServiceImpl.getInstance();

        CityUpdateDto cityUpdateDto = new CityUpdateDto(expectedCity.getId(), expectedCity.getName());
        service.update(cityUpdateDto);

        Mockito.verify(mokRepoInstance).update(expectedCity);
    }

    @Test
    void updateErrorTest() {
        CityService service = CityServiceImpl.getInstance();

        CityUpdateDto cityUpdateDto = new CityUpdateDto(null, "");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.update(cityUpdateDto);
        });

        Assertions.assertEquals("Empty city id", exception.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doReturn(true).when(mokRepoInstance).deleteById(Mockito.any(Long.class));
        mokStaticRepo.when(CityRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        CityService service = CityServiceImpl.getInstance();
        boolean isDeleted = service.delete(expectedCity.getId());

        Assertions.assertTrue(isDeleted);
    }
}