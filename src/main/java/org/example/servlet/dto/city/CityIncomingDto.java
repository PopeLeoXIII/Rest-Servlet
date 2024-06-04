package org.example.servlet.dto.city;

public class CityIncomingDto {
    private String name;

    public CityIncomingDto(){}

    public CityIncomingDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
