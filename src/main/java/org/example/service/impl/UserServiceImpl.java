package org.example.service.impl;

import org.example.NotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.mapper.UserMapper;
import org.example.service.mapper.impl.UserMapperImpl;
import org.example.service.UserService;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository repository = UserRepositoryImpl.getInstance();
    private final UserMapper mapper = UserMapperImpl.getInstance();
    private static UserService instance;

    private UserServiceImpl() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public UserOutGoingDto save(UserIncomingDto incomingDto) {
        User user = mapper.mapIncomingDto(incomingDto);
        user = repository.save(user);
        return mapper.mapModel(user);
    }

    @Override
    public void update(UserUpdateDto updateDto) throws NotFoundException {
        User user = mapper.mapUpdateDto(updateDto);
        repository.update(user);
    }

    @Override
    public UserOutGoingDto findById(Long id) throws NotFoundException {
        User user = repository.findById(id);
        return mapper.mapModel(user);
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        List<User> userList = repository.findAll();
        return mapper.mapModelList(userList);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
