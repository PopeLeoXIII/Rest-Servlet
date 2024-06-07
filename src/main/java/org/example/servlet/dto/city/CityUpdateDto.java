package org.example.servlet.dto.city;


public class CityUpdateDto {
    private Long id;
    private String name;

    public CityUpdateDto(){}

    public CityUpdateDto(Long id, String name) {
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
