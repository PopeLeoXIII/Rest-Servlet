package org.example.service.impl;

import org.example.model.*;
import org.example.repository.ReservationRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.impl.ReservationRepositoryImpl;
import org.example.service.ReservationService;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;
import org.example.servlet.dto.user.UserUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class ReservationServiceImplTest {
    private final User expectedUser = new User(1L, "name", "surname", List.of());
    private final List<Vehicle> expectedVehicles = List.of();
    private final Reservation expectedReservation =
            new Reservation(1L, Status.ACTIVE, "2016-06-22 19:10", "2016-06-23 19:10", expectedVehicles, expectedUser);

    private static MockedStatic<ReservationRepositoryImpl> mokStaticRepo;
    private static final ReservationRepository mokRepoInstance = mock(ReservationRepository.class);

    @BeforeEach
    public void setUp() {
        mokStaticRepo = mockStatic(ReservationRepositoryImpl.class);
    }

    @AfterEach
    public void tearDown() {
        mokStaticRepo.close();
    }

    @Test
    void saveTest() {
        Mockito.doReturn(expectedReservation).when(mokRepoInstance).save(Mockito.any(Reservation.class));
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                expectedReservation.getStatus(),
                expectedReservation.getStartDatetime().toString(),
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );

        ReservationOutGoingDto outGoingDto = service.save(incomingDto);

        Assertions.assertEquals(expectedReservation.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedReservation.getStatus(), outGoingDto.getStatus());
        Assertions.assertEquals(expectedReservation.getUser().getId(), outGoingDto.getUser().getId());
    }

    @Test
    void saveIllegalStatusTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                null,
                expectedReservation.getStartDatetime().toString(),
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty reservation status", exception.getMessage());
    }

    @Test
    void saveIllegalStartTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                Status.ACTIVE,
                "",
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty reservation start time", exception.getMessage());
    }

    @Test
    void saveIllegalEndTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                Status.ACTIVE,
                expectedReservation.getStartDatetime().toString(),
                "",
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty reservation end time", exception.getMessage());
    }

    @Test
    void saveIllegalStartWrongTest() {
    ReservationService service = ReservationServiceImpl.getInstance();

    ReservationIncomingDto incomingDto = new ReservationIncomingDto(
            Status.ACTIVE,
            "2016.06.22 19:10",
            expectedReservation.getEndDatetime().toString(),
            List.of(),
            new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
    );
    Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
        service.save(incomingDto);
    });

    Assertions.assertEquals("Wrong format reservation start time", exception.getMessage());
}

    @Test
    void saveIllegalEndWrongFormatTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                Status.ACTIVE,
                expectedReservation.getStartDatetime().toString(),
                "2016.06.22 19:10",
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Wrong format reservation end time", exception.getMessage());
    }

    @Test
    void saveIllegalUserTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationIncomingDto incomingDto = new ReservationIncomingDto(
                Status.ACTIVE,
                expectedReservation.getStartDatetime().toString(),
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(null, expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty reservation user id", exception.getMessage());
    }

    @Test
    void findByIdTest() throws NotFoundException {
        Mockito.doReturn(expectedReservation).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        ReservationService service = ReservationServiceImpl.getInstance();
        ReservationOutGoingDto outGoingDto = service.findById(expectedReservation.getId());

        Assertions.assertEquals(expectedReservation.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedReservation.getStatus(), outGoingDto.getStatus());
        Assertions.assertEquals(expectedReservation.getUser().getId(), outGoingDto.getUser().getId());
    }

    @Test
    void findByIdErrorTest() throws NotFoundException {
        Mockito.doThrow(NotFoundException.class).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        ReservationService service = ReservationServiceImpl.getInstance();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.findById(expectedReservation.getId());
        });
    }

    @Test
    void findAllTest() {
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);
        ReservationService service = ReservationServiceImpl.getInstance();
        service.findAll();

        Mockito.verify(mokRepoInstance).findAll();
    }

    @Test
    void updateTest() throws NotFoundException {
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationUpdateDto reservationUpdateDto = new ReservationUpdateDto(
                expectedReservation.getId(),
                expectedReservation.getStatus(),
                expectedReservation.getStartDatetime().toString(),
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        service.update(reservationUpdateDto);

        Mockito.verify(mokRepoInstance).update(expectedReservation);
    }

    @Test
    void updateErrorTest() {
        ReservationService service = ReservationServiceImpl.getInstance();

        ReservationUpdateDto reservationUpdateDto = new ReservationUpdateDto(
                null,
                expectedReservation.getStatus(),
                expectedReservation.getStartDatetime().toString(),
                expectedReservation.getEndDatetime().toString(),
                List.of(),
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname())
        );
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.update(reservationUpdateDto);
        });

        Assertions.assertEquals("Empty reservation id", exception.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doReturn(true).when(mokRepoInstance).deleteById(Mockito.any(Long.class));
        mokStaticRepo.when(ReservationRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        ReservationService service = ReservationServiceImpl.getInstance();
        boolean isDeleted = service.delete(expectedReservation.getId());

        Assertions.assertTrue(isDeleted);
    }
}