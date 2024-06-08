package org.example.service.impl;

import org.example.model.*;
import org.example.model.Vehicle;
import org.example.repository.VehicleRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.impl.VehicleRepositoryImpl;
import org.example.service.VehicleService;
import org.example.servlet.dto.city.CityUpdateDto;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class VehicleServiceImplTest {
    private final List<Reservation> expectedReservations = List.of();
    private final City expectedCity = new City(1L, "name", List.of());
    private final Vehicle expectedVehicle = new Vehicle(1L, "name", expectedCity, expectedReservations);

    private static MockedStatic<VehicleRepositoryImpl> mokStaticRepo;
    private static final VehicleRepository mokRepoInstance = mock(VehicleRepository.class);

    @BeforeEach
    public void setUp() {
        mokStaticRepo = mockStatic(VehicleRepositoryImpl.class);
    }

    @AfterEach
    public void tearDown() {
        mokStaticRepo.close();
    }

    @Test
    void saveTest() {
        Mockito.doReturn(expectedVehicle).when(mokRepoInstance).save(Mockito.any(Vehicle.class));
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        VehicleService service = VehicleServiceImpl.getInstance();

        VehicleIncomingDto incomingDto = new VehicleIncomingDto(
                expectedVehicle.getName(),
                new CityUpdateDto(expectedCity.getId(), expectedCity.getName())
        );

        VehicleOutGoingDto outGoingDto = service.save(incomingDto);

        Assertions.assertEquals(expectedVehicle.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedVehicle.getName(), outGoingDto.getName());
    }

    @Test
    void saveIllegalArgumentTest() {
        VehicleService service = VehicleServiceImpl.getInstance();

        VehicleIncomingDto incomingDto = new VehicleIncomingDto("", null);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty vehicle name", exception.getMessage());
    }

    @Test
    void findByIdTest() throws NotFoundException {
        Mockito.doReturn(expectedVehicle).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        VehicleService service = VehicleServiceImpl.getInstance();
        VehicleOutGoingDto outGoingDto = service.findById(expectedVehicle.getId());

        Assertions.assertEquals(expectedVehicle.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedVehicle.getName(), outGoingDto.getName());
    }

    @Test
    void findByIdErrorTest() throws NotFoundException {
        Mockito.doThrow(NotFoundException.class).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        VehicleService service = VehicleServiceImpl.getInstance();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.findById(expectedVehicle.getId());
        });
    }

    @Test
    void findAllTest() {
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);
        VehicleService service = VehicleServiceImpl.getInstance();
        service.findAll();

        Mockito.verify(mokRepoInstance).findAll();
    }

    @Test
    void updateTest() throws NotFoundException {
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        VehicleService service = VehicleServiceImpl.getInstance();

        VehicleUpdateDto vehicleUpdateDto = new VehicleUpdateDto(
                expectedVehicle.getId(),
                expectedVehicle.getName(),
                new CityUpdateDto(expectedCity.getId(), expectedCity.getName()));
        service.update(vehicleUpdateDto);

        Mockito.verify(mokRepoInstance).update(expectedVehicle);
    }

    @Test
    void updateErrorTest() {
        VehicleService service = VehicleServiceImpl.getInstance();

        VehicleUpdateDto vehicleUpdateDto = new VehicleUpdateDto(null, "", null);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.update(vehicleUpdateDto);
        });

        Assertions.assertEquals("Empty vehicle id", exception.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doReturn(true).when(mokRepoInstance).deleteById(Mockito.any(Long.class));
        mokStaticRepo.when(VehicleRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        VehicleService service = VehicleServiceImpl.getInstance();
        boolean isDeleted = service.delete(expectedVehicle.getId());

        Assertions.assertTrue(isDeleted);
    }
}