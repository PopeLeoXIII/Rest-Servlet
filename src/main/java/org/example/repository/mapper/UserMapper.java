package org.example.repository.mapper;

import org.example.model.User;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

public interface UserMapper extends TypedMapper<User, UserIncomingDto, UserOutGoingDto, UserUpdateDto> {
}
