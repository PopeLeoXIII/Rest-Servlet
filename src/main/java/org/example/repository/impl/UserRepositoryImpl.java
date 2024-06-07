package org.example.repository.impl;

import org.example.NotFoundException;
import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.City;
import org.example.model.User;
import org.example.repository.CityRepository;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery();) {
                if (rs.next()) {
                    return getUserFromResultSet(rs);
                } else {
                    throw new NotFoundException("No user with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private static User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                null);
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            try (ResultSet rs = s.executeQuery();) {
                List<User> userList = new ArrayList<>();
                while (rs.next()) {
                    userList.add(
                            getUserFromResultSet(rs));
                }
                return userList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User save(User user) {
        String query = "INSERT INTO users (name, surname) VALUES (?, ?);";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            s.setString(1, user.getName());
            s.setString(2, user.getSurname());
            s.executeUpdate();

            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return getUserFromResultSet(rs);
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
        String query = "UPDATE users SET name = ?, surname = ? WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setString(1, user.getName());
            s.setString(1, user.getSurname());
            s.setLong(2, user.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
