package org.example.service.mapper.impl;

import org.example.model.User;
import org.example.service.mapper.ReservationMapper;
import org.example.service.mapper.UserMapper;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.util.List;

public class UserMapperImpl implements UserMapper {
    private static final ReservationMapper reservationMapper = ReservationMapperImpl.getInstance();

    private static UserMapper instance;

    private UserMapperImpl() {
    }

    public static synchronized UserMapper getInstance() {
        if (instance == null) {
            instance = new UserMapperImpl();
        }
        return instance;
    }

    @Override
    public User mapIncomingDto(UserIncomingDto userIncomingDto) {
        return new User(
                null,
                userIncomingDto.getName(),
                userIncomingDto.getSurname(),
                null
        );
    }

    @Override
    public User mapUpdateDto(UserUpdateDto userUpdateDto) {
        return new User(
                userUpdateDto.getId(),
                userUpdateDto.getName(),
                userUpdateDto.getSurname(),
                userUpdateDto.getReservationList().stream().map(reservationMapper::mapUpdateDto).toList()
        );
    }

    @Override
    public UserOutGoingDto mapModel(User model) {
        if (model == null) {
            return new UserOutGoingDto();
        }

        return new UserOutGoingDto(
                model.getId(),
                model.getName(),
                model.getSurname(),
                reservationMapper.mapModelListToSmallDto(model.getReservationList())
        );
    }

    @Override
    public List<UserOutGoingDto> mapModelList(List<User> modelList) {
        return modelList.stream().map(this::mapModel).toList();
    }
}
