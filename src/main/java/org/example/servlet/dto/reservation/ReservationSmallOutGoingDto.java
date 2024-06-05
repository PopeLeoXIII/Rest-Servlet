package org.example.servlet.dto.reservation;

import org.example.model.Status;

public class ReservationSmallOutGoingDto {
    private Long id;
    private Status status;
    private String startDatetime;
    private String endDatetime;

    public ReservationSmallOutGoingDto(Long id, Status status, String startDatetime, String endDatetime) {
        this.id = id;
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
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
}
