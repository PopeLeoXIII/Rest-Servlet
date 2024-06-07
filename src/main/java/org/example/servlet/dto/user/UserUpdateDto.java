package org.example.servlet.dto.user;

import org.example.model.Reservation;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public class UserUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private List<ReservationUpdateDto> reservationList;

    public UserUpdateDto() {}

    public UserUpdateDto(Long id, String name, String surname, List<ReservationUpdateDto> reservationList) {
        this.id = id;
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

    public String getSurname() {
        return surname;
    }

    public List<ReservationUpdateDto> getReservationList() {
        return reservationList;
    }
}
