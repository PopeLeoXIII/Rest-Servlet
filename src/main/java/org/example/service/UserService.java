package org.example.service;

import org.example.exception.NotFoundException;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserOutGoingDto save(UserIncomingDto role);

    void update(UserUpdateDto role) throws NotFoundException;

    UserOutGoingDto findById(Long roleId) throws NotFoundException;

    List<UserOutGoingDto> findAll();

    boolean delete(Long roleId);
}
