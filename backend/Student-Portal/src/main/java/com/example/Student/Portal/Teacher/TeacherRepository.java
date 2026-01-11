package com.example.Student.Portal.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
    
    @Query("SELECT t FROM Teacher t WHERE t.email = :teacherId")
    Teacher findByTeacherId(@Param("teacherId") String teacherId);
    
    @Query("SELECT t FROM Teacher t WHERE t.email = :teacherId AND t.password = :password")
    Teacher findByTeacherIdAndPassword(@Param("teacherId") String teacherId, @Param("password") String password);
}