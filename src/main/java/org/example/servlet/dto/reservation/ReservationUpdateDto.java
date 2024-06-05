package org.example.servlet.dto.reservation;

import org.example.model.Status;
import org.example.model.User;
import org.example.model.Vehicle;
import org.example.servlet.dto.user.UserUpdateDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationUpdateDto {
    private Long id;
    private Status status;
    private String startDatetime;
    private String endDatetime;
    private List<VehicleUpdateDto> vehicleList;
    private UserUpdateDto user;

    public ReservationUpdateDto(Long id, Status status, String startDatetime, String endDatetime, List<VehicleUpdateDto> vehicleList, UserUpdateDto user) {
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

    public String getStartDatetime() {
        return startDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public List<VehicleUpdateDto> getVehicleList() {
        return vehicleList;
    }

    public UserUpdateDto getUser() {
        return user;
    }
}
