package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static ConnectionManager instance;

    static {
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/vehicle");
        config.setUsername("root");
        config.setPassword("123");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

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
