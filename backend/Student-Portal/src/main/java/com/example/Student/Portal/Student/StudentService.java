package com.example.Student.Portal.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student registerStudent(Student student) {
        if (studentRepo.findByStudentId(student.getStudentId()) != null) {
            return null; // already exists
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepo.save(student);
    }

    public Student login(String studentId, String rawPassword) {
        Student student = studentRepo.findByStudentId(studentId);
        if (student != null && passwordEncoder.matches(rawPassword, student.getPassword())) {
            return student;
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }





    public Student getByStudentId(String userId) {
        return studentRepo.findByStudentId(userId);
    }

    public Student findByEmail(String email) {
        return studentRepo.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }
}
