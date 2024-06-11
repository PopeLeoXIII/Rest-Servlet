package org.example.repository.impl;

import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.repository.SqlQuery.*;

public class UserRepositoryImpl implements UserRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
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
    public User findById(Long id) throws NotFoundException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_USER_BY_ID)) {

            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                } else {
                    throw new NotFoundException("No user with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_USER_ALL)) {

            try (ResultSet rs = s.executeQuery()) {
                List<User> userList = new ArrayList<>();
                while (rs.next()) {
                    userList.add(
                            createUserFromResultSet(rs));
                }
                return userList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, user.getName());
            s.setString(2, user.getSurname());
            s.executeUpdate();

            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                } else {
                    throw new RepositoryException("No create city id ");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(UPDATE_USER)) {

            s.setString(1, user.getName());
            s.setString(2, user.getSurname());
            s.setLong(3, user.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(DELETE_USER)) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private static User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                null);
    }
}
