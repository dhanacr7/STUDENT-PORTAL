package com.example.Student.Portal.Admin;
import jakarta.persistence.*;

@Entity
@Table(name = "admin_table")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private String email;

    @Transient // This field doesn't exist in your table, so make it transient
    private String adminId;

    private String password;

    // Default constructor
    public Admin() {}

    // Constructor with fields
    public Admin(String username, String email, String adminId, String password) {
        this.username = username;
        this.email = email;
        this.adminId = adminId;
        this.password = password;
    }

    // Getters and Setters 👇

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminId() {
        return adminId != null ? adminId : email; // Use email as adminId if not set
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}