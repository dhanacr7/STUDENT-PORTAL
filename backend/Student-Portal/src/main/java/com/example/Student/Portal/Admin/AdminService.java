package com.example.Student.Portal.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin registerAdmin(Admin admin) {
        if (adminRepo.findByAdminId(admin.getAdminId()) != null) {
            return null; // already exists
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepo.save(admin);
    }

    public Admin login(String adminId, String password) {
        return adminRepo.findByAdminIdAndPassword(adminId, password);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Admin getAdminById(String id) {
        return adminRepo.findByAdminId(id);
    }

    public Admin findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
