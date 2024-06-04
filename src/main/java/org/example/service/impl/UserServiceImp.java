package org.example.service.impl;

import org.example.NotFoundException;
import org.example.service.UserService;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.util.List;

public class UserServiceImp implements UserService {
    private static UserService instance;

    private UserServiceImp() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImp();
        }
        return instance;
    }

    @Override
    public UserOutGoingDto save(UserIncomingDto role) {
        return null;
    }

    @Override
    public void update(UserUpdateDto role) throws NotFoundException {

    }

    @Override
    public UserOutGoingDto findById(Long roleId) throws NotFoundException {
        return null;
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Long roleId) throws NotFoundException {
        return false;
    }
}
