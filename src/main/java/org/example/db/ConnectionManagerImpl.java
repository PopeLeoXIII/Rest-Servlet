package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
    private static final HikariConfig config = new HikariConfig("/db.properties");
    private static final HikariDataSource ds  = new HikariDataSource(config);
    private static ConnectionManager instance;

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManagerImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
