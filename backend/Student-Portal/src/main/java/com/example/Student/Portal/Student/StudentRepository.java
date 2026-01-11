package com.example.Student.Portal.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    
    @Query("SELECT s FROM Student s WHERE s.email = :studentId")
    Student findByStudentId(@Param("studentId") String studentId);
}