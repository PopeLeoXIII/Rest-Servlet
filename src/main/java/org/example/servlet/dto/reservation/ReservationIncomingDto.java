package org.example.servlet.dto.reservation;

import org.example.model.Status;

import java.time.LocalDateTime;

public class ReservationIncomingDto {
    private Status status;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    public ReservationIncomingDto(Status status, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.status = status;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
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
}
