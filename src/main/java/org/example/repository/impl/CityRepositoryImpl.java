package org.example.repository.impl;

import org.example.repository.exception.NotFoundException;
import org.example.repository.exception.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.City;
import org.example.repository.CityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.repository.SqlQuery.*;

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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_CITY_BY_ID)) {

            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    return new City(
                            rs.getLong("id"),
                            rs.getString("name"),
                            List.of());
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_CITY_ALL)) {

            try (ResultSet rs = s.executeQuery();) {
                List<City> cityList = new ArrayList<>();
                while (rs.next()) {
                    cityList.add(
                            new City(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    List.of()));
                }
                return cityList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public City save(City city) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(INSERT_CITY, Statement.RETURN_GENERATED_KEYS)) {

            s.setString(1, city.getName());
            s.executeUpdate();
            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return new City(
                            rs.getLong("id"),
                            city.getName(),
                            List.of());
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(UPDATE_CITY)) {

            s.setString(1, city.getName());
            s.setLong(2, city.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(DELETE_CITY);) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
