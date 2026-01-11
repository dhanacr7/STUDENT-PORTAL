package com.example.Student.Portal.Marks;

import jakarta.persistence.*;

@Entity
@Table(name = "marks")
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    private String subject;
    private Integer marks;

    public Marks() {}

    public Marks(Long studentId, String studentName, String subject, Integer marks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subject = subject;
        this.marks = marks;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
}