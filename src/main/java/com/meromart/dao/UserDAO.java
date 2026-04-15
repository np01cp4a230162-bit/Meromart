package com.meromart.dao;

import com.meromart.model.UserModel;
import com.meromart.util.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO — Data Access Object for the 'Users' table.
 *
 * VIVA NOTE:
 * DAO Pattern: isolates ALL SQL / database logic from the business logic (Controller/Service).
 * Controllers never write raw SQL; they call DAO methods and get back Java objects.
 * PreparedStatement is used everywhere to prevent SQL Injection attacks.
 */
public class UserDAO {

    /** Registers a new user. Returns true if the INSERT succeeded. */
    public boolean registerUser(UserModel user) {
        String sql = "INSERT INTO Users (name, email, password_hash, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getRole());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Looks up a user by email. Returns null if not found. */
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Returns true if a user with the given email already exists. */
    public boolean isEmailExists(String email) {
        String sql = "SELECT id FROM Users WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Returns true if a user with the given phone number already exists. */
    public boolean isPhoneExists(String phone) {
        String sql = "SELECT id FROM Users WHERE phone = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Maps a ResultSet row to a UserModel object. */
    private UserModel mapRow(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
