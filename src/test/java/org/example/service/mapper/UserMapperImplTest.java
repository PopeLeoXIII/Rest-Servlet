package org.example.service.mapper;

import org.example.model.Reservation;
import org.example.model.Status;
import org.example.model.User;
import org.example.service.mapper.impl.UserMapperImpl;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class UserMapperImplTest {
        private static final UserMapper mapper = UserMapperImpl.getInstance();
        private static final Long expectedId = 1L;
        private static final String expectedName = "Иван";
        private static final String expectedSurname = "Петров";
        private static final Reservation vehicle = new Reservation(2L, Status.ACTIVE, "2016-06-22 19:10", "2016-06-23 19:10", List.of(), null);
        private static final User expectedUser = new User(expectedId, expectedName, expectedSurname, List.of(vehicle));

        @Test
        void testGetInstance() {
            UserMapper userMapper = UserMapperImpl.getInstance();
            Assertions.assertNotNull(userMapper);
        }

        @Test
        void mapIncomingDtoTest() {
            UserIncomingDto userIncomingDto = new UserIncomingDto(expectedName, expectedSurname);
            User user = mapper.mapIncomingDto(userIncomingDto);
            Assertions.assertEquals(expectedName, user.getName());
        }

        @Test
        void mapUpdateDtoTest() {
            UserUpdateDto updateDto = new UserUpdateDto(expectedId, expectedName, expectedSurname);
            User user = mapper.mapUpdateDto(updateDto);
            Assertions.assertEquals(expectedId, user.getId());
            Assertions.assertEquals(expectedName, user.getName());
            Assertions.assertEquals(expectedSurname, user.getSurname());
        }

        @Test
        void mapModelTest() {
            UserOutGoingDto outgoingDto = mapper.mapModel(expectedUser);
            Assertions.assertEquals(expectedId, outgoingDto.getId());
            Assertions.assertEquals(expectedName, outgoingDto.getName());
            Assertions.assertFalse(outgoingDto.getReservationList().isEmpty());
        }

        @Test
        void testMapModel() {
            UserOutGoingDto outgoingDto = mapper.mapModel(expectedUser);

            Assertions.assertEquals(expectedId, outgoingDto.getId());
            Assertions.assertEquals(expectedName, outgoingDto.getName());
            Assertions.assertNotNull(outgoingDto.getReservationList());
        }

        @Test
        void testMapModelList() {
            List<User> modelList = Arrays.asList(
                    new User(2L, "Василий", "Таков", List.of(vehicle)),
                    new User(3L, "Олег", "Секов", List.of(vehicle)));

            List<UserOutGoingDto> outgoingList = mapper.mapModelList(modelList);

            Assertions.assertEquals(outgoingList.size(), modelList.size());
            for (int i = 0; i < modelList.size(); i++) {
                Assertions.assertEquals(outgoingList.get(i).getId(), modelList.get(i).getId());
                Assertions.assertEquals(outgoingList.get(i).getName(), modelList.get(i).getName());
                Assertions.assertNotNull(outgoingList.get(i).getReservationList());
            }

        }

    }