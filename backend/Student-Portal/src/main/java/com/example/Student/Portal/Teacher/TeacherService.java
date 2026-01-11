package com.example.Student.Portal.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Teacher registerTeacher(Teacher teacher) {
        if (teacherRepo.findByTeacherId(teacher.getTeacherId()) != null) {
            return null; // already exists
        }
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return teacherRepo.save(teacher);
    }

    public Teacher login(String teacherId, String password) {
        return teacherRepo.findByTeacherIdAndPassword(teacherId, password);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public Teacher getByTeacherId(String id) {
        return teacherRepo.findByTeacherId(id);
    }

    public Teacher findByEmail(String email) {
        return teacherRepo.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }
}
