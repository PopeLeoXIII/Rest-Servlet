package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.CityRepository;
import org.example.repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;

    private UserRepositoryImpl () {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }


    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void update(User user) {

    }
}
