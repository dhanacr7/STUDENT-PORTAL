package com.example.Student.Portal.Admin;

import com.example.Student.Portal.Student.Student;
import com.example.Student.Portal.Student.StudentService;
import com.example.Student.Portal.Teacher.Teacher;
import com.example.Student.Portal.Teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        try {
            System.out.println("Raw request received: " + request);
            
            String email = request.get("email");
            String password = request.get("password");
            
            if (email == null || password == null) {
                System.out.println("Missing email or password in request");
                return ResponseEntity.badRequest().body("Email and password are required");
            }
            
            System.out.println("Login attempt - Email: " + email + ", Password: " + password);
            
            // Find admin by email first
            Admin admin = adminService.findByEmail(email);
            if (admin == null) {
                System.out.println("Admin not found with email: " + email);
                return ResponseEntity.badRequest().body("Admin not found with this email");
            }
            
            System.out.println("Admin found: " + admin.getUsername());
            System.out.println("Stored password: " + admin.getPassword());
            
            // For existing plain text passwords, do direct comparison
            // For new BCrypt passwords, use BCrypt verification
            boolean isValidPassword = false;
            
            if (admin.getPassword().startsWith("$2a$") || admin.getPassword().startsWith("$2b$")) {
                // This is a BCrypt password
                isValidPassword = adminService.verifyPassword(password, admin.getPassword());
                System.out.println("BCrypt verification result: " + isValidPassword);
            } else {
                // This is a plain text password (existing data)
                isValidPassword = password.equals(admin.getPassword());
                System.out.println("Plain text comparison result: " + isValidPassword);
            }
            
            if (isValidPassword) {
                System.out.println("Login successful for: " + admin.getUsername());
                return ResponseEntity.ok("Login successful! Welcome, " + admin.getUsername());
            } else {
                System.out.println("Password mismatch");
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/add-teacher")
    public ResponseEntity<String> addTeacher(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String department = request.get("department");
            String teacherId = request.get("teacherId");
            String password = request.get("password");
            
            Teacher teacher = new Teacher(name, email, department, teacherId, password);
            Teacher saved = teacherService.registerTeacher(teacher);
            
            if (saved != null) {
                return ResponseEntity.ok("Teacher added successfully!");
            } else {
                return ResponseEntity.badRequest().body("Teacher ID already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add teacher: " + e.getMessage());
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String email = request.get("email");
            String adminId = request.get("adminId");
            String password = request.get("password");
            
            Admin admin = new Admin(username, email, adminId, password);
            Admin saved = adminService.registerAdmin(admin);
            
            if (saved != null) {
                return ResponseEntity.ok("Admin created successfully with BCrypt password!");
            } else {
                return ResponseEntity.badRequest().body("Admin ID already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create admin: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getStats() {
        try {
            Map<String, Integer> stats = new HashMap<>();
            stats.put("students", studentService.getAllStudents().size());
            stats.put("teachers", teacherService.getAllTeachers().size());
            stats.put("admins", adminService.getAllAdmins().size());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, Integer> errorStats = new HashMap<>();
            errorStats.put("students", 0);
            errorStats.put("teachers", 0);
            errorStats.put("admins", 0);
            return ResponseEntity.ok(errorStats);
        }
    }

    @GetMapping("/list-admins")
    public ResponseEntity<?> listAdmins() {
        try {
            var admins = adminService.getAllAdmins();
            System.out.println("Total admins in database: " + admins.size());
            for (Admin admin : admins) {
                System.out.println("Admin: " + admin.getUsername() + ", Email: " + admin.getEmail() + ", AdminId: " + admin.getAdminId());
            }
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            System.out.println("Error listing admins: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/list-teachers")
    public ResponseEntity<?> listTeachers() {
        try {
            var teachers = teacherService.getAllTeachers();
            System.out.println("Total teachers in database: " + teachers.size());
            return ResponseEntity.ok(teachers);
        } catch (Exception e) {
            System.out.println("Error listing teachers: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/list-students")
    public ResponseEntity<?> listStudents() {
        try {
            var students = studentService.getAllStudents();
            System.out.println("Total students in database: " + students.size());
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            System.out.println("Error listing students: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String department = request.get("department");
            String studentId = request.get("studentId");
            String password = request.get("password");
            
            Student student = new Student(name, email, department, studentId, password);
            Student saved = studentService.registerStudent(student);
            
            if (saved != null) {
                return ResponseEntity.ok("Student added successfully!");
            } else {
                return ResponseEntity.badRequest().body("Student email already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add student: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-teacher/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok("Teacher deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete teacher: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete student: " + e.getMessage());
        }
    }
}