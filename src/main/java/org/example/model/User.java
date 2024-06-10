package org.example.model;

import org.example.repository.ReservationRepository;
import org.example.repository.impl.ReservationRepositoryImpl;

import java.util.List;
import java.util.Objects;

/**
 * Пользователь системы
 * User {
 *     id - идентификатор пользователя в системе
 *     name - имя пользователя
 *     surname - фамилия пользователя
 * }
 * Relation:
 * One To Many: User -> Reservation
 */


public class User {
    private static final ReservationRepository reservationRepository = ReservationRepositoryImpl.getInstance();
    private Long id;
    private String name;
    private String surname;
    private List<Reservation> reservationList;

    public User(){}

    public User(Long userId, String name, String surname, List<Reservation> reservationList) {
        this.id = userId;
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Reservation> getReservationList() {
        if (reservationList == null) {
            this.reservationList = reservationRepository.findAllByUserId(this.id);
        }
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
