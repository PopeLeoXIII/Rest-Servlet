package org.example.model;

import org.example.repository.ReservationToVehicleRepository;
import org.example.repository.impl.ReservationToVehicleRepositoryImpl;

import java.util.List;

/**
 * Транспортное средство (пока что велосипеды, но вдруг еще что будет, поддерживаем мастшабируемость)
 * Vehicle {
 *     id
 *     city_id - город, в котором находится данная техника
 *     name - название техники
 *     (пока нет) image - изображение техники
 * }
 * Relation:
 * Many To Many: Vehicle <-> Reservation
 * Many To One: Vehicle -> City
 */

public class Vehicle {
    private static final ReservationToVehicleRepository reservationToVehicleRepository = ReservationToVehicleRepositoryImpl.getInstance();
    private Long id;
    private String name;
    private City city;
    private List<Reservation> reservationList;

    public Vehicle(){}

    public Vehicle(Long id, String name, City city, List<Reservation> reservationList) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.reservationList = reservationList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Reservation> getReservationList() {
        if (reservationList == null) {
            reservationList = reservationToVehicleRepository.getReservationByVehicleId(this.id);
        }
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
