package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String path = "src/main/java/resources/application.properties";
    private final String url = "spring.datasource.url";
    private final String username = "spring.datasource.username";
    private final String password = "spring.datasource.password";

    private static Connection connection;
    private static ConnectionManager instance;

    private ConnectionManager() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            connection = DriverManager.getConnection(properties.getProperty(url), properties.getProperty(username), properties.getProperty(password));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (instance == null)
            instance = new ConnectionManager();
        return connection;
    }
}
