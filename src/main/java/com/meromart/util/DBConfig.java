package com.meromart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig — centralised database connection factory.
 *
 * VIVA NOTE:
 * All JDBC connection details live in ONE place (Single Responsibility Principle).
 * Every DAO calls DBConfig.getConnection() instead of duplicating the URL/user/password.
 * If the DB host changes, you only update this one file.
 */
public class DBConfig {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL    = "jdbc:mysql://localhost:3306/webapp_db"
                                       + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER   = "root";
    private static final String PASS   = ""; // XAMPP default — change for production

    // Prevent instantiation — utility class with only static methods
    private DBConfig() {}

    /**
     * Returns a live JDBC Connection to the MeroMart database.
     * Callers MUST close this connection (preferably with try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found on classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
