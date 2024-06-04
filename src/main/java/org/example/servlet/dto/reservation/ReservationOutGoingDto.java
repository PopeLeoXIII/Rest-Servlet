package org.example.servlet.dto.reservation;

import org.example.model.Status;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationOutGoingDto {
    private Long id;
    private Status status;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private List<VehicleOutGoingDto> vehicleList;
    private UserOutGoingDto user;

    public ReservationOutGoingDto(Long id, Status status, LocalDateTime startDatetime, LocalDateTime endDatetime, List<VehicleOutGoingDto> vehicleList, UserOutGoingDto user) {
        this.id = id;
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.vehicleList = vehicleList;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public List<VehicleOutGoingDto> getVehicleList() {
        return vehicleList;
    }

    public UserOutGoingDto getUser() {
        return user;
    }
}
