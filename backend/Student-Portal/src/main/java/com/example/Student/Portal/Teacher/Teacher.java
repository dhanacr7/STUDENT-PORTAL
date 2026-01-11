package com.example.Student.Portal.Teacher;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_table")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private String email;

    @Transient // This field doesn't exist in your table
    private String department;

    @Transient // This field doesn't exist in your table
    private String teacherId;

    private String password;

    // Default constructor
    public Teacher() {}

    // Constructor with fields (optional)
    public Teacher(String name, String email, String department, String teacherId, String password) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.teacherId = teacherId;
        this.password = password;
    }

    // Getters and Setters 👇

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}