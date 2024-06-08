package org.example.service.impl;

import org.example.repository.exception.NotFoundException;
import org.example.model.Reservation;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
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

public class UserServiceImplTest {
    private final List<Reservation> expectedReservation = List.of();
    private final User expectedUser = new User(1L, "name", "surname", expectedReservation);
    private static MockedStatic<UserRepositoryImpl> mokStaticRepo;
    private static final UserRepository mokRepoInstance = mock(UserRepository.class);


    @BeforeEach
    public void setUp() {
        mokStaticRepo = mockStatic(UserRepositoryImpl.class);
    }

    @AfterEach
    public void tearDown() {
        mokStaticRepo.close();
    }

    @Test
    void saveTest() {
        Mockito.doReturn(expectedUser).when(mokRepoInstance).save(Mockito.any(User.class));
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        UserService service = UserServiceImpl.getInstance();

        UserIncomingDto incomingDto = new UserIncomingDto(expectedUser.getName(), expectedUser.getSurname());
        UserOutGoingDto outGoingDto = service.save(incomingDto);

        Assertions.assertEquals(expectedUser.getId(), outGoingDto.getId());
        Assertions.assertEquals(expectedUser.getName(), outGoingDto.getName());
    }

    @Test
    void saveErrorTest() {
        UserService service = UserServiceImpl.getInstance();

        UserIncomingDto incomingDto = new UserIncomingDto("", "");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.save(incomingDto);
        });

        Assertions.assertEquals("Empty user name", exception.getMessage());
    }

    @Test
    void findByIdTest() throws NotFoundException {
        Mockito.doReturn(expectedUser).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        UserService service = UserServiceImpl.getInstance();
        UserOutGoingDto outGoingDto = service.findById(expectedUser.getId());

        Assertions.assertEquals(
                expectedUser,
                new User(outGoingDto.getId(), outGoingDto.getName(), outGoingDto.getSurname(), List.of())
        );
    }

    @Test
    void findByIdErrorTest() throws NotFoundException {
        Mockito.doThrow(NotFoundException.class).when(mokRepoInstance).findById(Mockito.any(Long.class));
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        UserService service = UserServiceImpl.getInstance();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.findById(expectedUser.getId());
        });
    }

    @Test
    void findAllTest() {
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);
        UserService service = UserServiceImpl.getInstance();
        service.findAll();

        Mockito.verify(mokRepoInstance).findAll();
    }

    @Test
    void updateTest() throws NotFoundException {
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        UserService service = UserServiceImpl.getInstance();

        UserUpdateDto userUpdateDto =
                new UserUpdateDto(expectedUser.getId(), expectedUser.getName(), expectedUser.getSurname());
        service.update(userUpdateDto);

        Mockito.verify(mokRepoInstance).update(expectedUser);
    }

    @Test
    void updateErrorTest() {
        UserService service = UserServiceImpl.getInstance();

        UserUpdateDto userUpdateDto = new UserUpdateDto(null, "", "");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.update(userUpdateDto);
        });

        Assertions.assertEquals("Empty user id", exception.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doReturn(true).when(mokRepoInstance).deleteById(Mockito.any(Long.class));
        mokStaticRepo.when(UserRepositoryImpl::getInstance).thenReturn(mokRepoInstance);

        UserService service = UserServiceImpl.getInstance();
        boolean isDeleted = service.delete(expectedUser.getId());

        Assertions.assertTrue(isDeleted);
    }
}