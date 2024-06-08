package org.example.repository;

import org.example.exception.NotFoundException;

import java.util.List;

public interface SimpleRepository<T, K> {
    T findById(K id) throws NotFoundException;

    boolean deleteById(K id);

    List<T> findAll();

    T save(T t);

    void update(T t);
}
