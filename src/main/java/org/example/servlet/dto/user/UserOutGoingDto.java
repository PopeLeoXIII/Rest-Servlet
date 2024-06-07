package org.example.servlet.dto.user;

import org.example.servlet.dto.reservation.ReservationSmallOutGoingDto;

import java.util.List;

public class UserOutGoingDto {
    private Long id;
    private String name;
    private String surname;
    private List<ReservationSmallOutGoingDto> reservationList;

    public UserOutGoingDto() {}

    public UserOutGoingDto(Long id, String name, String surname, List<ReservationSmallOutGoingDto> reservationList) {
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

    public List<ReservationSmallOutGoingDto> getReservationList() {
        return reservationList;
    }
}
