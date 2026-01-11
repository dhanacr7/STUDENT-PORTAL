package com.example.Student.Portal.Student;

import com.example.Student.Portal.Marks.Marks;
import com.example.Student.Portal.Marks.MarksService;
import com.example.Student.Portal.Attendance.Attendance;
import com.example.Student.Portal.Attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String password = request.get("password");
            
            // Generate student ID from email (you can modify this logic)
            String studentId = email.split("@")[0];
            
            Student student = new Student(name, email, "General", studentId, password);
            Student saved = studentService.registerStudent(student);
            
            if (saved != null) {
                return ResponseEntity.ok("Student registered successfully!");
            } else {
                return ResponseEntity.badRequest().body("Student ID already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            System.out.println("Student login - Raw request received: " + request);
            
            String email = request.get("email");
            String password = request.get("password");
            
            if (email == null || password == null) {
                System.out.println("Missing email or password in student login request");
                return ResponseEntity.badRequest().body("Email and password are required");
            }
            
            System.out.println("Student login attempt - Email: " + email + ", Password: " + password);
            
            // Find student by email first
            Student student = studentService.findByEmail(email);
            if (student == null) {
                System.out.println("Student not found with email: " + email);
                return ResponseEntity.badRequest().body("Student not found with this email");
            }
            
            System.out.println("Student found: " + student.getName());
            System.out.println("Stored password: " + student.getPassword());
            
            // For existing plain text passwords, do direct comparison
            // For new BCrypt passwords, use BCrypt verification
            boolean isValidPassword = false;
            
            if (student.getPassword().startsWith("$2a$") || student.getPassword().startsWith("$2b$")) {
                // This is a BCrypt password
                isValidPassword = studentService.verifyPassword(password, student.getPassword());
                System.out.println("BCrypt verification result: " + isValidPassword);
            } else {
                // This is a plain text password (existing data)
                isValidPassword = password.equals(student.getPassword());
                System.out.println("Plain text comparison result: " + isValidPassword);
            }
            
            if (isValidPassword) {
                System.out.println("Student login successful for: " + student.getName());
                return ResponseEntity.ok(student);
            } else {
                System.out.println("Student password mismatch");
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } catch (Exception e) {
            System.out.println("Student login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/marks")
    public ResponseEntity<?> getMarks(@PathVariable Long id) {
        try {
            List<Marks> marks = marksService.getMarksByStudentId(id);
            return ResponseEntity.ok(marks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching marks: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/attendance")
    public ResponseEntity<?> getAttendance(@PathVariable Long id) {
        try {
            List<Attendance> attendance = attendanceService.getAttendanceByStudentId(id);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching attendance: " + e.getMessage());
        }
    }
}