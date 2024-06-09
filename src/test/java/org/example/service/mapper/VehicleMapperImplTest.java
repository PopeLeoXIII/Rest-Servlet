package org.example.service.mapper;

import org.example.model.*;
import org.example.service.mapper.impl.VehicleMapperImpl;
import org.example.servlet.dto.city.CityUpdateDto;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class VehicleMapperImplTest {
    private static final VehicleMapper mapper = VehicleMapperImpl.getInstance();
    private static final Long expectedId = 1L;
    private static final String expectedName = "Велосипед";
    private static final City expectedCity = new City(2L, "Москва", List.of());
    private static final Reservation reservation = new Reservation(2L, Status.ACTIVE, "2016-06-22 19:10", "2016-06-23 19:10", List.of(), null);
    private static final Vehicle expectedVehicle = new Vehicle(expectedId, expectedName, expectedCity, List.of(reservation));

    @Test
    void testGetInstance() {
        VehicleMapper vehicleMapper = VehicleMapperImpl.getInstance();
        Assertions.assertNotNull(vehicleMapper);
    }

    @Test
    void mapIncomingDtoTest() {
        VehicleIncomingDto incomingDto = new VehicleIncomingDto(
                expectedName,
                new CityUpdateDto(expectedCity.getId(), expectedCity.getName()));
        Vehicle vehicle = mapper.mapIncomingDto(incomingDto);
        Assertions.assertEquals(expectedName, vehicle.getName());
    }

    @Test
    void mapUpdateDtoTest() {
        VehicleUpdateDto updateDto = new VehicleUpdateDto(
                expectedId,
                expectedName,
                new CityUpdateDto(expectedCity.getId(), expectedCity.getName()));
        Vehicle vehicle = mapper.mapUpdateDto(updateDto);
        Assertions.assertEquals(expectedId, vehicle.getId());
        Assertions.assertEquals(expectedName, vehicle.getName());
        Assertions.assertEquals(expectedCity.getId(), vehicle.getCity().getId());
    }

    @Test
    void mapModelTest() {
        VehicleOutGoingDto outgoingDto = mapper.mapModel(expectedVehicle);
        Assertions.assertEquals(expectedId, outgoingDto.getId());
        Assertions.assertEquals(expectedName, outgoingDto.getName());
        Assertions.assertFalse(outgoingDto.getReservationList().isEmpty());
    }

    @Test
    void mapModelListTest() {
        List<Vehicle> modelList = Arrays.asList(
                new Vehicle(2L, "Самокат", expectedCity, List.of(reservation)),
                new Vehicle(3L, "Скутер", expectedCity, List.of(reservation)));

        List<VehicleOutGoingDto> outgoingList = mapper.mapModelList(modelList);

        Assertions.assertEquals(outgoingList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            Assertions.assertEquals(outgoingList.get(i).getId(), modelList.get(i).getId());
            Assertions.assertEquals(outgoingList.get(i).getName(), modelList.get(i).getName());
            Assertions.assertEquals(outgoingList.get(i).getCity().getId(), modelList.get(i).getCity().getId());
            Assertions.assertNotNull(outgoingList.get(i).getReservationList());
        }
    }

    @Test
    void mapModelToPlaneDtoTest() {
        VehiclePlaneDto planeDto = mapper.mapModelToPlaneDto(expectedVehicle);
        Assertions.assertEquals(expectedId, planeDto.getId());
        Assertions.assertEquals(expectedName, planeDto.getName());
    }

    @Test
    void mapModelListToPlaneDtoTest() {
        List<Vehicle> modelList = Arrays.asList(
                new Vehicle(2L, "Самокат", expectedCity, List.of(reservation)),
                new Vehicle(3L, "Скутер", expectedCity, List.of(reservation)));

        List<VehiclePlaneDto> planeDtos = mapper.mapModelListToPlaneDto(modelList);

        Assertions.assertEquals(planeDtos.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            Assertions.assertEquals(planeDtos.get(i).getId(), modelList.get(i).getId());
            Assertions.assertEquals(planeDtos.get(i).getName(), modelList.get(i).getName());
        }
    }

    @Test
    void mapPlaneDtoToVehicleTest() {
        VehiclePlaneDto planeDto = new VehiclePlaneDto(
                expectedId,
                expectedName);
        Vehicle vehicle = mapper.mapPlaneDtoToVehicle(planeDto);
        Assertions.assertEquals(expectedId, vehicle.getId());
        Assertions.assertEquals(expectedName, vehicle.getName());
        Assertions.assertNull(vehicle.getCity());
        Assertions.assertTrue(vehicle.getReservationList().isEmpty());
    }

    @Test
    void mapPlaneDtoListToModelTest() {
        List<VehiclePlaneDto> planeDtoList = Arrays.asList(
                new VehiclePlaneDto(2L, "Самокат"),
                new VehiclePlaneDto(3L, "Скутер"));

        List<Vehicle> vehicles = mapper.mapPlaneDtoListToModel(planeDtoList);

        Assertions.assertEquals(vehicles.size(), planeDtoList.size());
        for (int i = 0; i < planeDtoList.size(); i++) {
            Assertions.assertEquals(planeDtoList.get(i).getId(), vehicles.get(i).getId());
            Assertions.assertEquals(planeDtoList.get(i).getName(), vehicles.get(i).getName());
        }
    }
}
