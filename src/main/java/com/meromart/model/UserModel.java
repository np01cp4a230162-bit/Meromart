package com.meromart.model;

import java.io.Serializable;

/**
 * UserModel (JavaBean) — maps to the 'users' table in the database.
 * Follows JavaBean conventions: private fields, public getters/setters, no-arg constructor.
 * Implements Serializable so it can be stored in the HttpSession.
 */
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private String role; // 'admin' or 'user'

    public UserModel() {}

    public UserModel(String name, String email, String passwordHash, String phone, String role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.role = role;
    }

    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }

    public String getName()                 { return name; }
    public void setName(String name)        { this.name = name; }

    public String getEmail()                { return email; }
    public void setEmail(String email)      { this.email = email; }

    public String getPasswordHash()                         { return passwordHash; }
    public void setPasswordHash(String passwordHash)        { this.passwordHash = passwordHash; }

    public String getPhone()                { return phone; }
    public void setPhone(String phone)      { this.phone = phone; }

    public String getRole()                 { return role; }
    public void setRole(String role)        { this.role = role; }

    /** Convenience helper used by JSPs via EL: ${user.admin} */
    public boolean isAdmin()                { return "admin".equalsIgnoreCase(role); }

    @Override
    public String toString() {
        return "UserModel{id=" + id + ", name='" + name + "', email='" + email + "', role='" + role + "'}";
    }
}
