package org.example.repository.impl;

import org.example.model.*;
import org.example.repository.ReservationRepository;
import org.example.repository.ReservationToVehicleRepository;
import org.example.repository.TestcontainerManager;
import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;


@Testcontainers
@Tag("DockerRequired")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationRepositoryImplTest {
    public static ReservationRepository repository = ReservationRepositoryImpl.getInstance();
    public static ReservationToVehicleRepository joinTableRepository = ReservationToVehicleRepositoryImpl.getInstance();

    private static final City expectedCity = new City(1L, "Moscow", List.of());
    private static final User expectedUser = new User(5L, "Максим", "Четверкин", List.of());
    private static final Vehicle expectedVehicle = new Vehicle(3L, "Велосипед 1", expectedCity, List.of());
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

    @BeforeAll
    static void beforeAll() {
        TestcontainerManager.start();
    }

    @AfterAll
    static void afterAll() {
        TestcontainerManager.stop();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void findAll() {
        int expectedSize = 4;
        int resultSize = repository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(2)
    void saveTest() throws NotFoundException {
        Reservation reservation = expectedReservation;

        reservation = repository.save(reservation);

        Reservation newReservation = repository.findById(reservation.getId());

        Assertions.assertEquals(Status.ACTIVE, newReservation.getStatus());
        Assertions.assertTrue(newReservation.getStartDatetime().toString().contains(expectedStartDatetime));
        Assertions.assertTrue(newReservation.getEndDatetime().toString().contains(expectedEndDatetime));
        Assertions.assertEquals(expectedUser.getId(), newReservation.getUser().getId());
        Assertions.assertEquals(1, newReservation.getVehicleList().size());

        List<Vehicle> vehicleList = joinTableRepository.findVehicleListByReservationId(newReservation.getId());
        Assertions.assertEquals(vehicleList.size(), newReservation.getVehicleList().size());
    }

    @Test
    @Order(3)
    void saveNullTest() {
        Map<String, Reservation> map = new HashMap<>();
        map.put("status", new Reservation(1L, null, expectedStartDatetime, expectedEndDatetime, List.of(expectedVehicle), expectedUser));
        map.put("start_datetime", new Reservation(1L, Status.ACTIVE, null, expectedEndDatetime, List.of(expectedVehicle), expectedUser));
        map.put("end_datetime", new Reservation(1L, Status.ACTIVE, expectedStartDatetime, null, List.of(expectedVehicle), expectedUser));
        map.put("user_id", new Reservation(1L, Status.ACTIVE, expectedStartDatetime, expectedEndDatetime, List.of(expectedVehicle), null));

        map.forEach((k, v) -> {
            Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.save(v));

            Assertions.assertTrue(exception.getMessage().contains("ERROR: null value in column \"" + k +"\" of relation \"reservations\""));
        });
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void findByIdTest(Long id) throws NotFoundException {
        Reservation reservation = repository.findById(id);

        Assertions.assertEquals(id, reservation.getId());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 6, 13, 14})
    void findByIdErrorTest(Long id) {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            repository.findById(id);
        });

        Assertions.assertEquals("No reservation with id " + id, exception.getMessage());
    }

    @Order(4)
    @Test
    void findAllByUserIdTest() throws NotFoundException {
        Long id = expectedUser.getId();
        List<Reservation> reservationsByUserId = repository.findAllByUserId(id);

        User user = UserRepositoryImpl.getInstance().findById(id);

        long count = repository.findAll().stream()
                .filter(r -> Objects.equals(r.getUser().getId(), id)).count();

        Assertions.assertEquals(user.getReservationList().size(), reservationsByUserId.size());
        Assertions.assertEquals(count, reservationsByUserId.size());
    }

    @Order(4)
    @Test
    void updateTest() throws NotFoundException {
        Reservation r = new Reservation(
                null,
                Status.ACTIVE,
                expectedStartDatetime,
                expectedEndDatetime,
                List.of(expectedVehicle),
                expectedUser);
        Reservation reservation = repository.save(r);
        Assertions.assertEquals(1, reservation.getVehicleList().size());

        reservation.setStatus(Status.CANCELED);

        List<Vehicle> vehicleList = new ArrayList<>(reservation.getVehicleList());
        vehicleList.add(new Vehicle(5L, "2", null, null));
        vehicleList.add(new Vehicle(4L, "1", null, null));
        reservation.setVehicleList(vehicleList);

        repository.update(reservation);

        reservation = repository.findById(reservation.getId());

        Assertions.assertEquals(Status.CANCELED, reservation.getStatus());
        Assertions.assertEquals(expectedReservation.getUser().getId(), reservation.getUser().getId());

        List<Vehicle> actualVehicle = joinTableRepository.findVehicleListByReservationId(reservation.getId());
        Assertions.assertEquals(actualVehicle.size(), vehicleList.size());
    }

    @Order(4)
    @Test
    void updateEmptyVehicleListTest() throws NotFoundException {
        expectedReservation.setVehicleList(List.of(
                expectedVehicle,
                new Vehicle(5L, "2", null, null),
                new Vehicle(4L, "1", null, null)
        ));
        Reservation reservation = repository.save(expectedReservation);
        Assertions.assertEquals(3, reservation.getVehicleList().size());

        reservation.setVehicleList(List.of());
        Long newUserId = 6L;
        reservation.setUser(UserRepositoryImpl.getInstance().findById(newUserId));

        repository.update(reservation);

        Reservation newReservation = repository.findById(reservation.getId());

        Assertions.assertEquals(reservation.getId(), newReservation.getId());
        Assertions.assertEquals(newUserId, newReservation.getUser().getId());
        Assertions.assertEquals(Status.ACTIVE, newReservation.getStatus());

        List<Vehicle> actualVehicle = joinTableRepository.findVehicleListByReservationId(reservation.getId());
        Assertions.assertTrue(actualVehicle.isEmpty());
    }

    @Order(4)
    @Test
    void updateChangeVehicleListTest() throws NotFoundException {
        expectedReservation.setVehicleList(List.of(
                expectedVehicle,
                new Vehicle(5L, null, null, null),
                new Vehicle(4L, null, null, null)
        ));
        Reservation reservation = repository.save(expectedReservation);
        Assertions.assertEquals(3, reservation.getVehicleList().size());

        reservation.setVehicleList(List.of(expectedVehicle));
        repository.update(reservation);

        Reservation newReservation = repository.findById(reservation.getId());

        Assertions.assertEquals(reservation.getId(), newReservation.getId());
        Assertions.assertEquals(Status.ACTIVE, newReservation.getStatus());

        List<Vehicle> actualVehicle = joinTableRepository.findVehicleListByReservationId(reservation.getId());
        Assertions.assertEquals(1, actualVehicle.size());
        Assertions.assertEquals(actualVehicle.size(), newReservation.getVehicleList().size());
        Assertions.assertEquals(expectedVehicle.getId(), newReservation.getVehicleList().get(0).getId());
    }


    @Order(5)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 4, 5, 8, 10})
    void deleteTest(Long id) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertTrue(isDeleted);
    }

    @Order(6)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 12, 15})
    void deleteErrorTest(Long id) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertFalse(isDeleted);
    }
}