package org.example.repository.impl;

import org.example.model.City;
import org.example.repository.CityRepository;
import org.example.repository.TestcontainerManager;
import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Tag("DockerRequired")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CityRepositoryImplTest {
    public static CityRepository repository;

    @BeforeAll
    static void beforeAll() {
        TestcontainerManager.start();
        repository = CityRepositoryImpl.getInstance();
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
        int expectedSize = 3;
        int resultSize = repository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(2)
    void saveTest() throws NotFoundException {
        String expectedName = "new City Name";
        City city = new City(null, expectedName, null);
        city = repository.save(city);
        City newCity = repository.findById(city.getId());

        Assertions.assertEquals(expectedName, newCity.getName());
    }

    @Test
    @Order(3)
    void saveNullTest() {
        String expectedName = null;
        City city = new City(null, expectedName, null);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.save(city));

        Assertions.assertTrue(exception.getMessage().contains("ERROR: null value in column \"name\" of relation \"citys\""));
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void findByIdTest(Long id) throws NotFoundException {
        City city = repository.findById(id);

        Assertions.assertEquals(id, city.getId());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 5, 13, 14})
    void findByIdErrorTest(Long id) {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> repository.findById(id));

        Assertions.assertEquals("No city with id " + id, exception.getMessage());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void updateTest(Long id) throws NotFoundException {
        String expectedName = "update City Name";
        City city = new City(id, expectedName, null);
        repository.update(city);

        city = repository.findById(id);

        Assertions.assertEquals(expectedName, city.getName());
    }

    @Order(5)
    @ParameterizedTest
    @ValueSource(longs = {4})
    void deleteTest(Long id) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertTrue(isDeleted);
    }

    @Order(6)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 1, 2, 3, 4})
    void deleteErrorTest(Long id) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertFalse(isDeleted);
    }
}