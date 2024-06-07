package org.example.servlet.dto.reservation;

import org.example.model.Status;

public class ReservationIncomingDto {
    private Status status;
    private String startDatetime;
    private String endDatetime;

    public ReservationIncomingDto() {}

    public ReservationIncomingDto(Status status, String startDatetime, String endDatetime) {
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
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
}
