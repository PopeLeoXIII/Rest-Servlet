package org.example.repository.impl;

import org.example.NotFoundException;
import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.City;
import org.example.model.Reservation;
import org.example.model.Vehicle;
import org.example.repository.ReservationToVehicleRepository;
import org.example.repository.VehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.repository.SqlQuery.*;

public class VehicleRepositoryImpl implements VehicleRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private final ReservationToVehicleRepository rToVRepository = ReservationToVehicleRepositoryImpl.getInstance();
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement getVehicle = connection.prepareStatement(SELECT_VEHICLE_BY_ID);) {

            List<Reservation> reservationList = rToVRepository.getReservationByVehicleId(id);

            getVehicle.setLong(1, id);
            try (ResultSet rs = getVehicle.executeQuery();) {
                if (rs.next()) {
                    return createVehicle(id, rs, reservationList);
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement getVehicle = connection.prepareStatement(SELECT_VEHICLE_ALL)) {

            try (ResultSet rs = getVehicle.executeQuery();) {
                List<Vehicle> vehicleList = new ArrayList<>();
                while (rs.next()) {
                    long id = rs.getLong("id");
                    List<Reservation> reservationList = rToVRepository.getReservationByVehicleId(id);

                    vehicleList.add(createVehicle(id, rs, reservationList));
                }
                return vehicleList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private static Vehicle createVehicle(Long id, ResultSet rs, List<Reservation> reservationList) throws SQLException {
        return new Vehicle(
                id,
                rs.getString("name"),
                new City(
                        rs.getLong("city_id"),
                        rs.getString("city_name"),
                        null
                ),
                reservationList);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(INSERT_VEHICLE, Statement.RETURN_GENERATED_KEYS);) {

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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(UPDATE_VEHICLE);) {

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

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(DELETE_VEHICLE);) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Vehicle> findAllByCityId(City city) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_VEHICLE_BY_CITY_ID);) {
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
