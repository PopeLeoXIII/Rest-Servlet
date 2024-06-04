package org.example.servlet.dto.vehicle;

public class VehicleUpdateDto {
    private Long id;
    private String name;

    public VehicleUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
