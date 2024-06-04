package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public User findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }
}
