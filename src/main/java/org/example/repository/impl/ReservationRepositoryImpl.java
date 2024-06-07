package org.example.repository.impl;

import org.example.NotFoundException;
import org.example.RepositoryException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.*;
import org.example.repository.ReservationRepository;
import org.example.repository.ReservationToVehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.repository.SqlQuery.*;

public class ReservationRepositoryImpl implements ReservationRepository {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private final ReservationToVehicleRepository rToVRepository = ReservationToVehicleRepositoryImpl.getInstance();
    private static ReservationRepository instance;
    private ReservationRepositoryImpl() {
    }

    public static synchronized ReservationRepository getInstance() {
        if (instance == null) {
            instance = new ReservationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Reservation findById(Long id) throws NotFoundException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement getReservation = connection.prepareStatement(SELECT_USER_JOIN_RES_BY_ID);) {

            List<Vehicle> vehicleList = rToVRepository.getVehicleByReservationId(id);

            getReservation.setLong(1, id);
            try (ResultSet rs = getReservation.executeQuery();) {
                if (rs.next()) {
                    return createReservation(id, rs, vehicleList);
                } else {
                    throw new NotFoundException("No reservation with id " + id);
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement getReservation = connection.prepareStatement(SELECT_USER_JOIN_RES_ALL);) {

            try (ResultSet rs = getReservation.executeQuery();) {
                List<Reservation> reservationList = new ArrayList<>();
                while (rs.next()) {
                    long id = rs.getLong("id");
                    List<Vehicle> vehicleList = rToVRepository.getVehicleByReservationId(id);

                    reservationList.add(
                            createReservation(id, rs, vehicleList)
                    );
                }
                return reservationList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private static Reservation createReservation(Long id, ResultSet rs, List<Vehicle> vehicleList) throws SQLException {
        return new Reservation(
                id,
                Status.valueOf(rs.getString("status")),
                rs.getTimestamp("start_datetime"),
                rs.getTimestamp("end_datetime"),
                vehicleList,
                new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        null
                ));
    }


    @Override
    public Reservation save(Reservation reservation) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(INSERT_RES, Statement.RETURN_GENERATED_KEYS);) {

            connection.setAutoCommit(false);

            s.setString(1, reservation.getStatus().toString());
            s.setTimestamp(2, reservation.getStartDatetime());
            s.setTimestamp(3, reservation.getEndDatetime());
            if (reservation.getUser() == null) {
                s.setNull(4, Types.NULL);
            } else {
                s.setLong(4, reservation.getUser().getId());
            }

            s.executeUpdate();

            if (reservation.getVehicleList() != null && !reservation.getVehicleList().isEmpty()) {
                updateReservationVehicle(reservation);
            }

            try (ResultSet rs = s.getGeneratedKeys()) {
                if (rs.next()) {
                    connection.commit();
                    return new Reservation(
                            rs.getLong("id"),
                            reservation.getStatus(),
                            reservation.getStartDatetime(),
                            reservation.getEndDatetime(),
                            null,
                            reservation.getUser());
                } else {
                    throw new RepositoryException("No create reservation id ");
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Reservation reservation) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateReservation = connection.prepareStatement(UPDATE_RES)) {

            connection.setAutoCommit(false);

            if (reservation.getVehicleList() != null && !reservation.getVehicleList().isEmpty()) {
                updateReservationVehicle(reservation);
            }

            updateReservation.setString(1, reservation.getStatus().toString());
            updateReservation.setTimestamp(2, reservation.getStartDatetime());
            updateReservation.setTimestamp(3, reservation.getEndDatetime());
            if (reservation.getUser() == null) {
                updateReservation.setNull(4, Types.NULL);
            } else {
                updateReservation.setLong(4, reservation.getUser().getId());
            }
            updateReservation.setLong(5, reservation.getId());
            updateReservation.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    private void updateReservationVehicle(Reservation reservation) {
        List<Vehicle> vehicleList =
                rToVRepository.getVehicleByReservationId(reservation.getId());

        List<Long> oldVehicleId = vehicleList.stream().map(Vehicle::getId).toList();
        List<Long> newVehicleId = reservation.getVehicleList().stream().map(Vehicle::getId).toList();

        oldVehicleId.stream()
                .filter(id -> !newVehicleId.contains(id))
                .forEach(id -> rToVRepository.deleteByData(
                        new ReservationToVehicle(null, reservation.getId(), id)));

        newVehicleId.stream()
                .filter(id -> !oldVehicleId.contains(id))
                .forEach(id -> rToVRepository.save(
                        new ReservationToVehicle(null, reservation.getId(), id)));
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(DELETE_RES);) {

            s.setLong(1, id);
            return s.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> findAllByUserId(User user) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement s = connection.prepareStatement(SELECT_RES_BY_USER_ID);) {

            s.setLong(1, user.getId());
            try (ResultSet rs = s.executeQuery();) {
                List<Reservation> reservationList = new ArrayList<>();
                while (rs.next()) {
                    reservationList.add(
                            new Reservation(
                                    rs.getLong("id"),
                                    Status.valueOf(rs.getString("status")),
                                    rs.getTimestamp("start_datetime"),
                                    rs.getTimestamp("end_datetime"),
                                    null,
                                    null)
                    );
                }
                return reservationList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
