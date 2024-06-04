package org.example.model;

/**
 * ManyToMany
 * Reservation <-> Vehicle
 */

public class ReservationToVehicle {
    private Long id;
    private Long reservationId;
    private Long vehicleId;

    public ReservationToVehicle(){}

    public ReservationToVehicle(Long id, Long reservationId, Long vehicleId) {
        this.id = id;
        this.reservationId = reservationId;
        this.vehicleId = vehicleId;
    }

    public Long getId() {
        return id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
