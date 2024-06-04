package org.example.servlet.dto.reservation;

import org.example.model.Status;
import org.example.model.User;
import org.example.model.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationSmallOutGoingDto {
    private Long id;
    private Status status;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    public ReservationSmallOutGoingDto(Long id, Status status, LocalDateTime startDatetime, LocalDateTime endDatetime) {
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

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }
}
