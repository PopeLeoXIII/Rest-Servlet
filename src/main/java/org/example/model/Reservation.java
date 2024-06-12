package org.example.model;

import org.example.repository.ReservationToVehicleRepository;
import org.example.repository.impl.ReservationToVehicleRepositoryImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Бронирование
 * Reservation {
 *     id
 *     vehicle_ids (M-to-M) - список забронированной техники
 *     user_id (M-to-O) - пользователь, кто забронировал
 *     startDatetime - с какого времени
 *     endDatetime - до какого времени
 *     status [ACTIVE, CANCELED] - статус бронирования (активно / отменено)
 * }
 * Relation:
 * Many To Many: Reservation <-> Vehicle
 * One To Many: Reservation -> User
 */

public class Reservation {
    private Long id;
    private Status status;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private List<Vehicle> vehicleList;
    private User user;

    public Reservation() {}

    public Reservation(Long id, Status status, Timestamp startDatetime, Timestamp endDatetime, List<Vehicle> vehicleList, User user) {
        this.id = id;
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.vehicleList = vehicleList;
        this.user = user;
    }

    public Reservation(Long id, Status status, String startDatetime, String endDatetime, List<Vehicle> vehicleList, User user) {
        this.id = id;
        this.status = status;
        this.startDatetime = stringToTimestamp(startDatetime);
        this.endDatetime = stringToTimestamp(endDatetime);
        this.vehicleList = vehicleList;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    public List<Vehicle> getVehicleList() {
        if (vehicleList == null && id != null) {
            ReservationToVehicleRepository reservationToVehicleRepository = ReservationToVehicleRepositoryImpl.getInstance();
            vehicleList =
                    reservationToVehicleRepository.findVehicleListByReservationId(this.id);
        }
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Timestamp stringToTimestamp(String stringDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(stringDate);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(startDatetime, that.startDatetime) && Objects.equals(endDatetime, that.endDatetime) && Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, startDatetime, endDatetime, user);
    }
}
