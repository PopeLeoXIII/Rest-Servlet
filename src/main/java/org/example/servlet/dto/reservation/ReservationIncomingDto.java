package org.example.servlet.dto.reservation;

import org.example.model.Status;
import org.example.servlet.dto.user.UserUpdateDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;


public class ReservationIncomingDto {
    private Status status;
    private String startDatetime;
    private String endDatetime;
    private UserUpdateDto user;
    private List<VehiclePlaneDto> vehicleList;

    public ReservationIncomingDto() {}

    public ReservationIncomingDto(Status status, String startDatetime, String endDatetime, UserUpdateDto user, List<VehiclePlaneDto> vehicles) {
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.user = user;
        this.vehicleList = vehicles;
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

    public UserUpdateDto getUser() {
        return user;
    }

    public List<VehiclePlaneDto> getVehicleList() {
        return vehicleList;
    }
}
