package com.example.Student.Portal.Student;

import jakarta.persistence.*;

@Entity
@Table(name = "student_table")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    
    private String email;
    
    @Transient // This field doesn't exist in your table
    private String department;
    
    @Transient // This field doesn't exist in your table
    private String studentId;
    
    private String password;

    public Student() {}

    public Student(String name, String email, String department, String studentId, String password) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.studentId = studentId;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
