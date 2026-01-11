package com.example.Student.Portal.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    
    @Query("SELECT a FROM Admin a WHERE a.email = :adminId")
    Admin findByAdminId(@Param("adminId") String adminId);
    
    @Query("SELECT a FROM Admin a WHERE a.email = :adminId AND a.password = :password")
    Admin findByAdminIdAndPassword(@Param("adminId") String adminId, @Param("password") String password);
}