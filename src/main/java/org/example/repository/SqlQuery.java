package org.example.repository;

public class SqlQuery {
    public static final String SELECT_VEHICLES_BY_RES_ID =
            "SELECT vehicle_id, name " +
            "FROM reservations_vehicles rv JOIN vehicles v " +
            "ON v.id = rv.vehicle_id " +
            "WHERE rv.reservation_id = ?";

    public static final String SELECT_USER_JOIN_RES_BY_ID =
            "SELECT status, start_datetime, end_datetime, user_id, name, surname " +
            "FROM reservations r JOIN users u " +
            "ON r.user_id = u.id " +
            "WHERE r.id = ?";

    public static final String SELECT_USER_JOIN_RES_ALL =
            "SELECT r.id, status, start_datetime, end_datetime, user_id, name, surname " +
            "FROM reservations r JOIN users u " +
            "ON r.user_id = u.id";

    public static final String INSERT_RES =
            "INSERT INTO reservations (status, start_datetime, end_datetime, user_id) VALUES (?, ?, ?, ?);";

    public static final String DELETE_RES =
            "DELETE FROM reservations WHERE id = ?";

    public static final String SELECT_RES_BY_USER_ID =
            "SELECT id, status, start_datetime, end_datetime FROM reservations WHERE user_id = ?;";

    public static final String UPDATE_RES =
            "UPDATE reservations SET status = ?, start_datetime = ?, end_datetime = ?, user_id = ? WHERE id = ?;";

    public static final String DELETE_R_TO_V =
            "DELETE FROM reservations_vehicles WHERE reservation_id = ? AND vehicle_id = ?";

    public static final String INSERT_R_TO_V =
            "INSERT INTO reservations_vehicles (reservation_id, vehicle_id) VALUES (?, ?)";

    public static final String SELECT_VEHICLE_BY_ID =
            "SELECT v.name AS \"name\", city_id, c.name AS \"city_name\" " +
            "FROM vehicles v JOIN citys c " +
            "ON v.city_id = c.id " +
            "WHERE v.id = ?";


    public static final String SELECT_RES_BY_VEHICLE_ID =
            "SELECT reservation_id, status, start_datetime, end_datetime " +
            "FROM reservations_vehicles rv JOIN reservations r " +
            "ON r.id = rv.reservation_id " +
            "WHERE rv.vehicle_id = ?";

    public static final String SELECT_VEHICLE_ALL =
            "SELECT v.name AS \"name\", city_id, c.name AS \"city_name\" " +
            "FROM vehicles v JOIN citys c " +
            "ON v.city_id = c.id";

    public static final String INSERT_VEHICLE =
            "INSERT INTO vehicles (name, city_id) VALUES (?, ?);";


    public static final String UPDATE_VEHICLE =
            "UPDATE vehicles SET name = ?, city_id = ? WHERE id = ?;";

    public static final String DELETE_VEHICLE =
            "DELETE FROM vehicles WHERE id = ?";


    public static final String SELECT_VEHICLE_BY_CITY_ID =
            "SELECT id, name FROM vehicles WHERE city_id = ?;";


    public static final String SELECT_CITY_BY_ID =
            "SELECT id, name FROM citys WHERE id = ?";

    public static final String SELECT_CITY_ALL =
            "SELECT id, name FROM citys";

    public static final String INSERT_CITY =
            "INSERT INTO citys (name) VALUES (?);";


    public static final String UPDATE_CITY =
            "UPDATE citys SET name = ? WHERE id = ?";

    public static final String DELETE_CITY =
            "DELETE FROM citys WHERE id = ?";


}



