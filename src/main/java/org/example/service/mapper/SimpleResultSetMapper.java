package org.example.service.mapper;

import org.example.model.SimpleEntity;

import java.sql.ResultSet;

public interface SimpleResultSetMapper {
    SimpleEntity map(ResultSet resultSet);
}
