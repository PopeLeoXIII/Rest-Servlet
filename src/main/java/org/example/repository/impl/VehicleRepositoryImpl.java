package org.example.repository.impl;

import org.example.NotFoundException;
import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.City;
import org.example.model.Vehicle;
import org.example.repository.VehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepositoryImpl implements VehicleRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static VehicleRepository instance;
    private VehicleRepositoryImpl() {
    }

    public static synchronized VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Vehicle findById(Long id) throws NotFoundException {
        String query = "SELECT vehicles.id AS \"id\", vehicles.name AS \"name\", citys.id AS \"city_id\", citys.name AS \"city_name\" \n" +
                "FROM vehicles JOIN citys ON vehicles.city_id = citys.id \n" +
                "WHERE vehicles.id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery();) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getLong("id"),
                            rs.getString("name"),
                            new City(
                                    rs.getLong("city_id"),
                                    rs.getString("city_name"),
                                    null
                            ),
                            null);
                } else {
                    throw new NotFoundException("No vehicle with id " + id);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        String query = "SELECT vehicles.id AS \"id\", vehicles.name AS \"name\", citys.id AS \"city_id\", citys.name AS \"city_name\" \n" +
                "FROM vehicles JOIN citys ON vehicles.city_id = citys.id \n";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            try (ResultSet rs = s.executeQuery();) {
                List<Vehicle> vehicleList = new ArrayList<>();
                while (rs.next()) {
                    vehicleList.add(
                            new Vehicle(
                                rs.getLong("id"),
                                rs.getString("name"),
                                new City(
                                        rs.getLong("city_id"),
                                        rs.getString("city_name"),
                                        null
                                ),
                                null));
                }
                return vehicleList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (name, city_id) VALUES (?, ?);";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

            s.setString(1, vehicle.getName());
            if (vehicle.getCity() == null) {
                s.setNull(2, Types.NULL);
            } else {
                s.setLong(2, vehicle.getCity().getId());
            }

            s.executeUpdate();
            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getLong("id"),
                            vehicle.getName(),
                            vehicle.getCity(),
                            null);
                } else {
                    throw new RepositoryException("No create city id ");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Vehicle vehicle) {
        String query = "UPDATE vehicles SET name = ?, city_id = ? WHERE id = ?;";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setString(1, vehicle.getName());
            if (vehicle.getCity() == null) {
                s.setNull(2, Types.NULL);
            } else {
                s.setLong(2, vehicle.getCity().getId());
            }
            s.setLong(3, vehicle.getId());

            s.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM vehicles WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Vehicle> findAllByCityId(City city) {
        String query = "SELECT * FROM vehicles WHERE city_id = ?;";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(query);) {
            s.setLong(1, city.getId());
            try (ResultSet rs = s.executeQuery();) {
                List<Vehicle> vehicleList = new ArrayList<>();
                while (rs.next()) {
                    vehicleList.add(
                            new Vehicle(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    new City(
                                            city.getId(),
                                            city.getName(),
                                            null
                                    ),
                                    null));
                }
                return vehicleList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
