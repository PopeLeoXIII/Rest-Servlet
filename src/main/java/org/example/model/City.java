package org.example.model;

import org.example.repository.VehicleRepository;
import org.example.repository.impl.VehicleRepositoryImpl;

import java.util.List;

/**
 * Город, в котором существует техника для бронирования
 * City {
 *     id
 *     name - название города
 *     (пока нет) timezone - таймзона города
 *  }
 * Relation:
 * One To Many: City -> Vehicle
 */

public class City {
    private static final VehicleRepository vehicleRepository = VehicleRepositoryImpl.getInstance();
    private Long id;
    private String name;
    private List<Vehicle> vehicleList;

    public City(){}

    public City(Long id, String name, List<Vehicle> vehicleList) {
        this.id = id;
        this.name = name;
        this.vehicleList = vehicleList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vehicle> getVehicleList() {
        if (vehicleList == null) {
            this.vehicleList = vehicleRepository.findAllByCityId(this.id);
        }
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
