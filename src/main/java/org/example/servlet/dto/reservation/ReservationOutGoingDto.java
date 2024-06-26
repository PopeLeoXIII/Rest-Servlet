package org.example.servlet.dto.reservation;

import org.example.model.Status;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;

import java.util.List;

public class ReservationOutGoingDto {
    private Long id;
    private Status status;
    private String startDatetime;
    private String endDatetime;
    private List<VehiclePlaneDto> vehicleList;
    private UserOutGoingDto user;

    public ReservationOutGoingDto() {}

    public ReservationOutGoingDto(Long id, Status status, String startDatetime, String endDatetime, List<VehiclePlaneDto> vehicleList, UserOutGoingDto user) {
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

    public List<VehiclePlaneDto> getVehicleList() {
        return vehicleList;
    }

    public UserOutGoingDto getUser() {
        return user;
    }
}
