package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.TestcontainerManager;
import org.example.repository.UserRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Tag("DockerRequired")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {
    public static UserRepository repository;

    @BeforeAll
    static void beforeAll() {
        TestcontainerManager.start();
        repository = UserRepositoryImpl.getInstance();
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
        int expectedSize = 7;
        int resultSize = repository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(2)
    void saveTest() throws NotFoundException {
        String expectedName = "new User Name";
        String expectedSurname = "new User Surname";
        User user = new User(null, expectedName, expectedSurname, null);
        user = repository.save(user);
        User newUser = repository.findById(user.getId());

        Assertions.assertEquals(expectedName, newUser.getName());
        Assertions.assertEquals(expectedSurname, newUser.getSurname());
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource(value = {
            "true; true",
            "true; false",
            "false; true"
    }, delimiter = ';')
    void saveNullTest(boolean nameIsNull, boolean surnameIsNull) {
        String name = nameIsNull ? null : "Валерий";
        String surname = surnameIsNull ? null : "Лошкарев";
        User user = new User(null, name, surname, null);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.save(user));

        Assertions.assertTrue(
                exception.getMessage().contains("ERROR: null value in column \"name\" of relation \"users\"") ||
                exception.getMessage().contains("ERROR: null value in column \"surname\" of relation \"users\""));
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 5, 8})
    void findByIdTest(Long id) throws NotFoundException {
        User user = repository.findById(id);

        Assertions.assertEquals(id, user.getId());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 10, 13, 140})
    void findByIdErrorTest(Long id) {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> repository.findById(id));

        Assertions.assertEquals("No user with id " + id, exception.getMessage());
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {1, 4, 5, 8})
    void updateTest(Long id) throws NotFoundException {
        String expectedName = "update User Name";
        String expectedSurname = "update User Surname";
        User user = new User(id, expectedName, expectedSurname, null);
        repository.update(user);

        user = repository.findById(id);

        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedSurname, user.getSurname());
    }

    @Order(5)
    @ParameterizedTest
    @CsvSource(value = {
            "-1; false",
            "0; false",
            "5; true",
            "4; true",
            "15; false",
            "68; false"
    }, delimiter = ';')
    void deleteTest(Long id, boolean result) {
        boolean isDeleted = repository.deleteById(id);

        Assertions.assertEquals(result, isDeleted);
    }

    @Order(6)
    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    void deleteErrorTest(Long id) {
        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.deleteById(id));

        Assertions.assertTrue(
                exception.getMessage().contains("Key (id)=(" + id + ") is still referenced from table \"reservations\"."));
    }
}