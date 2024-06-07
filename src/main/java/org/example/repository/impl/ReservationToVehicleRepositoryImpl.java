package org.example.repository.impl;

import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Reservation;
import org.example.model.ReservationToVehicle;
import org.example.model.Status;
import org.example.model.Vehicle;
import org.example.repository.ReservationToVehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.repository.SqlQuery.*;

public class ReservationToVehicleRepositoryImpl implements ReservationToVehicleRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private static ReservationToVehicleRepository instance;
    private ReservationToVehicleRepositoryImpl() {
    }

    public static synchronized ReservationToVehicleRepository getInstance() {
        if (instance == null) {
            instance = new ReservationToVehicleRepositoryImpl();
        }
        return instance;
    }

    @Override
    public ReservationToVehicle save(ReservationToVehicle reservationToVehicle) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(INSERT_R_TO_V, Statement.RETURN_GENERATED_KEYS);) {

            s.setLong(1, reservationToVehicle.getReservationId());
            s.setLong(2, reservationToVehicle.getVehicleId());
            s.executeUpdate();

            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    return new ReservationToVehicle(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3));
                } else {
                    throw new RepositoryException("No create join table entry ");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Vehicle> getVehicleByReservationId(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_VEHICLES_BY_RES_ID)) {
            List<Vehicle> vehicleList = new ArrayList<>();
            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery();) {
                while (rs.next()) {
                    vehicleList.add(
                            new Vehicle(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    null,
                                    null
                            ));
                }
            }
            return vehicleList;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public List<Reservation> getReservationByVehicleId(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_RES_BY_VEHICLE_ID)) {
            List<Reservation> reservationList = new ArrayList<>();
            s.setLong(1, id);
            try (ResultSet rs = s.executeQuery();) {
                while (rs.next()) {
                    reservationList.add(
                            new Reservation(
                                    id,
                                    Status.valueOf(rs.getString("status")),
                                    rs.getTimestamp("start_datetime"),
                                    rs.getTimestamp("end_datetime"),
                                    null,
                                    null
                            ));
                }
            }
            return reservationList;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public boolean deleteByData(ReservationToVehicle reservationToVehicle) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(DELETE_R_TO_V);) {

            s.setLong(1, reservationToVehicle.getReservationId());
            s.setLong(2, reservationToVehicle.getVehicleId());
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
