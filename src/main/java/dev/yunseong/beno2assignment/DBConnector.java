package dev.yunseong.beno2assignment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnector {
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER_CLASS_NAME;

    public Connection getConnection() {
        try {
            Class.forName(DB_DRIVER_CLASS_NAME);
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}
