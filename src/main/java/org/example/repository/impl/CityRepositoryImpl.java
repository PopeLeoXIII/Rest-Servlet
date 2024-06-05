package org.example.repository.impl;

import org.example.model.City;
import org.example.repository.CityRepository;

public class CityRepositoryImpl implements CityRepository {
    private static CityRepository instance;

    private CityRepositoryImpl () {
    }

    public static synchronized CityRepository getInstance() {
        if (instance == null) {
            instance = new CityRepositoryImpl();
        }
        return instance;
    }


    @Override
    public City findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public City findAll() {
        return null;
    }

    @Override
    public City save(City city) {
        return null;
    }
}
