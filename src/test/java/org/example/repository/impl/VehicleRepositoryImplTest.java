package org.example.repository.impl;

import org.example.db.ConnectionManagerImpl;
import org.example.model.*;
import org.example.repository.VehicleRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

@Testcontainers
@Tag("DockerRequired")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehicleRepositoryImplTest {
    public static VehicleRepository repository = VehicleRepositoryImpl.getInstance();
    private static final String INIT_SQL = "db-migration.sql";

    private static final String expectedName = "Велосипед 1";
    private static final Long expectedId = 3L;

    private static final City expectedCity = new City(1L, "Moscow", List.of());
    private static final Vehicle expectedVehicle = new Vehicle(expectedId, expectedName, expectedCity, List.of());


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("vehicle")
            .withUsername("root")
            .withPassword("123")
            .withInitScript(INIT_SQL);

    @BeforeAll
    static void beforeAll() {
        postgres.start();

        final String url = postgres.getJdbcUrl();
        ConnectionManagerImpl.setRemote(url);
    }

    @AfterAll
    static void afterAll() {
        ConnectionManagerImpl.setLocal();
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void findAll() {
        int expectedSize = 5;
        int resultSize = repository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(2)
    void saveTest() {
        String newName = "Мото байк";

        Vehicle vehicle = repository.save(new Vehicle(null, newName, expectedCity, List.of()));

        Assertions.assertEquals(newName, vehicle.getName());
        Assertions.assertEquals(expectedCity.getId(), vehicle.getCity().getId());
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource(value = {
            "true; true",
            "true; false",
            "false; true"
    }, delimiter = ';')
    void saveNullTest(boolean nameIsNull, boolean cityIsNull) {
        String name = nameIsNull ? null : "Валерий";
        City city = cityIsNull ? null : expectedCity;
        Vehicle vehicle = new Vehicle(null, name, city, null);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> {
            repository.save(vehicle);
        });
        System.out.println(exception.getMessage());

        Assertions.assertTrue(exception.getMessage().contains("ERROR: null value in column \"name\" of relation \"vehicles\"")
            || exception.getMessage().contains("ERROR: null value in column \"city_id\" of relation \"vehicles\""));
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 5})
    void findByIdTest(Long id) throws NotFoundException {
        Vehicle vehicle = repository.findById(id);

        Assertions.assertEquals(id, vehicle.getId());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 10, 13, 140})
    void findByIdErrorTest(Long id) {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            repository.findById(id);
        });

        Assertions.assertEquals("No vehicle with id " + id, exception.getMessage());
    }


    @Order(4)
    @Test
    void findAllByCityIdTest() throws NotFoundException {
        Long id = expectedCity.getId();
        List<Vehicle> vehicleByCityId = repository.findAllByCityId(id);

        City city = CityRepositoryImpl.getInstance().findById(id);

        long count = repository.findAll().stream()
                .filter(r -> Objects.equals(r.getCity().getId(), id)).count();

        Assertions.assertEquals(city.getVehicleList().size(), vehicleByCityId.size());
        Assertions.assertEquals(count, vehicleByCityId.size());
    }

    @Test
    @Order(4)
    void updateTest() throws NotFoundException {
        Long id = 3L;
        Vehicle vehicle = repository.findById(id);

        String name = "Велосипед из Москвы";
        vehicle.setName(name);
        City city = new City(2L, null, null);
        vehicle.setCity(city);
        List<Reservation> reservations = vehicle.getReservationList();

        repository.update(vehicle);

        vehicle = repository.findById(id);

        Assertions.assertEquals(name, vehicle.getName());
        Assertions.assertEquals(city.getId(), vehicle.getCity().getId());

        List<Reservation> actualReservations = ReservationToVehicleRepositoryImpl.getInstance().findReservationListByVehicleId(id);

        Assertions.assertEquals(reservations.size(), actualReservations.size());
    }

    @Order(5)
    @ParameterizedTest
    @CsvSource(value = {
            "-1; false",
            "0; false",
            "1; true",
            "2; true",
            "5; true",
            "4; true",
            "15; false",
            "68; false"
    }, delimiter = ';')
    void deleteTest(Long id, boolean result) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertEquals(result, isDeleted);

        repository.findAll().forEach(v -> System.out.println(v.getId()));
    }

}