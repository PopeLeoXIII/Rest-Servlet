package org.example.service.mapper;

import org.example.model.*;
import org.example.model.Reservation;
import org.example.service.mapper.impl.ReservationMapperImpl;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;
import org.example.servlet.dto.user.UserUpdateDto;
import org.example.servlet.dto.reservation.ReservationPlaneDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ReservationMapperImplTest {
    private static final ReservationMapper mapper = ReservationMapperImpl.getInstance();
    private static final City expectedCity = new City(2L, "Москва", List.of());
    private static final User expectedUser = new User(3L, "Евгений", "Моржов", List.of());
    private static final Vehicle expectedVehicle = new Vehicle(3L, "Велосипед", expectedCity, List.of());
    private static final Long expectedId = 1L;
    private static final String expectedStartDatetime = "2016-06-22 19:10";
    private static final String expectedEndDatetime = "2016-06-23 19:10";
    private static final Reservation expectedReservation = new Reservation(
            expectedId,
            Status.ACTIVE,
            expectedStartDatetime,
            expectedEndDatetime,
            List.of(expectedVehicle),
            expectedUser);

    @Test
    void testGetInstance() {
        ReservationMapper reservationMapper = ReservationMapperImpl.getInstance();
        Assertions.assertNotNull(reservationMapper);
    }

    @Test
    void mapIncomingDtoTest() {
        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                Status.ACTIVE,
                expectedStartDatetime,
                expectedEndDatetime,
                List.of(new VehiclePlaneDto(expectedVehicle.getId(), expectedVehicle.getName())),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname()));

        Reservation reservation = mapper.mapIncomingDto(incomingDto);

        Assertions.assertEquals(Status.ACTIVE, reservation.getStatus());
        Assertions.assertTrue(reservation.getStartDatetime().toString().contains(expectedStartDatetime));
        Assertions.assertTrue(reservation.getEndDatetime().toString().contains(expectedEndDatetime));
        Assertions.assertEquals(expectedUser.getId(), reservation.getUser().getId());
        Assertions.assertEquals(incomingDto.getVehicleList().size(), reservation.getVehicleList().size());
    }

    @Test
    void mapUpdateDtoTest() {
        ReservationUpdateDto updateDto = new ReservationUpdateDto(
                expectedId,
                Status.ACTIVE,
                expectedStartDatetime,
                expectedEndDatetime,
                List.of(new VehiclePlaneDto(expectedVehicle.getId(), expectedVehicle.getName())),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname()));

        Reservation reservation = mapper.mapUpdateDto(updateDto);

        Assertions.assertEquals(expectedId, reservation.getId());
        Assertions.assertEquals(Status.ACTIVE, reservation.getStatus());
        Assertions.assertTrue(reservation.getStartDatetime().toString().contains(expectedStartDatetime));
        Assertions.assertTrue(reservation.getEndDatetime().toString().contains(expectedEndDatetime));
        Assertions.assertEquals(expectedUser.getId(), reservation.getUser().getId());
        Assertions.assertEquals(updateDto.getVehicleList().size(), reservation.getVehicleList().size());
    }

    @Test
    void mapModelTest() {
        ReservationOutGoingDto outgoingDto = mapper.mapModel(expectedReservation);
        Assertions.assertEquals(expectedId, outgoingDto.getId());
        Assertions.assertEquals(Status.ACTIVE, outgoingDto.getStatus());
        Assertions.assertTrue(outgoingDto.getStartDatetime().contains(expectedStartDatetime));
        Assertions.assertTrue(outgoingDto.getEndDatetime().contains(expectedEndDatetime));
        Assertions.assertEquals(expectedUser.getId(), outgoingDto.getUser().getId());
        Assertions.assertEquals(expectedReservation.getVehicleList().size(), outgoingDto.getVehicleList().size());
    }

    @Test
    void mapModelListTest() {
        List<Reservation> modelList = Arrays.asList(
                new Reservation(2L, Status.ACTIVE, expectedStartDatetime, expectedEndDatetime, List.of(expectedVehicle), expectedUser),
                new Reservation(3L, Status.CANCELED, expectedStartDatetime , expectedEndDatetime, List.of(), expectedUser));

        List<ReservationOutGoingDto> outgoingList = mapper.mapModelList(modelList);

        Assertions.assertEquals(outgoingList.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            Assertions.assertEquals(modelList.get(i).getId(), outgoingList.get(i).getId());
            Assertions.assertEquals(modelList.get(i).getStatus(), outgoingList.get(i).getStatus());
            Assertions.assertEquals(modelList.get(i).getStartDatetime().toString(), outgoingList.get(i).getStartDatetime());
            Assertions.assertEquals(modelList.get(i).getEndDatetime().toString(), outgoingList.get(i).getEndDatetime());
            Assertions.assertEquals(modelList.get(i).getUser().getId(), outgoingList.get(i).getUser().getId());
            Assertions.assertEquals(modelList.get(i).getVehicleList().size(), outgoingList.get(i).getVehicleList().size());

        }
    }

    @Test
    void mapModelToPlaneDtoTest() {
        ReservationPlaneDto planeDto = mapper.mapModelToPlaneDto(expectedReservation);
        Assertions.assertEquals(expectedId, planeDto.getId());
        Assertions.assertEquals(Status.ACTIVE, planeDto.getStatus());
        Assertions.assertTrue(planeDto.getStartDatetime().contains(expectedStartDatetime));
        Assertions.assertTrue(planeDto.getEndDatetime().contains(expectedEndDatetime));
    }

    @Test
    void mapModelListToPlaneDtoTest() {
        List<Reservation> modelList = Arrays.asList(
                new Reservation(2L, Status.ACTIVE, expectedStartDatetime, expectedEndDatetime, List.of(expectedVehicle), expectedUser),
                new Reservation(3L, Status.CANCELED, expectedStartDatetime , expectedEndDatetime, List.of(), expectedUser));

        List<ReservationPlaneDto> planeDtos = mapper.mapModelListToPlaneDto(modelList);

        Assertions.assertEquals(planeDtos.size(), modelList.size());
        for (int i = 0; i < modelList.size(); i++) {
            Assertions.assertEquals(modelList.get(i).getId(), planeDtos.get(i).getId());
            Assertions.assertEquals(modelList.get(i).getStatus(), planeDtos.get(i).getStatus());
            Assertions.assertEquals(modelList.get(i).getStartDatetime().toString(), planeDtos.get(i).getStartDatetime());
            Assertions.assertEquals(modelList.get(i).getEndDatetime().toString(), planeDtos.get(i).getEndDatetime());
        }
    }
}