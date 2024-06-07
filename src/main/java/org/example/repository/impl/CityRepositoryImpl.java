package org.example.repository.impl;

import org.example.NotFoundException;
import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.City;
import org.example.repository.CityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepositoryImpl implements CityRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static CityRepository instance;

    private CityRepositoryImpl () {
    }

    public static synchronized CityRepository getInstance() {
        if (instance == null) {
            instance = new CityRepositoryImpl();
        }
        return instance;
    }

    @Override
    public City findById(Long id) throws NotFoundException {
        String query = "SELECT * FROM citys WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery();) {
                if (rs.next()) {
                    return new City(rs.getLong("id"), rs.getString("name"), null);
                } else {
                    throw new NotFoundException("No city with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<City> findAll() {
        String query = "SELECT * FROM citys";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            try (ResultSet rs = s.executeQuery();) {
                List<City> cityList = new ArrayList<>();
                while (rs.next()) {
                    cityList.add(new City(rs.getLong("id"), rs.getString("name"), null));
                }
                return cityList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public City save(City city) {
        String query = "INSERT INTO citys (name) VALUES (?);";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            s.setString(1, city.getName());
            s.executeUpdate();
            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return new City(rs.getLong("id"),  city.getName(), null);
                } else {
                    throw new RepositoryException("No create city id ");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(City city) {
        String query = "UPDATE citys SET name = ? WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setString(1, city.getName());
            s.setLong(2, city.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM citys WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            boolean ok = s.executeUpdate() > 0;
            return ok;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
