-- Sample SQL queries to insert marks with subjects into the database
-- Run these in your MySQL database (student_portal)

USE student_portal;

-- Insert sample marks data with different subjects
INSERT INTO marks (student_id, student_name, subject, marks) VALUES
(1, 'John Doe', 'Mathematics', 85),
(1, 'John Doe', 'Physics', 78),
(1, 'John Doe', 'Chemistry', 92),
(2, 'Jane Smith', 'Mathematics', 90),
(2, 'Jane Smith', 'English', 88),
(2, 'Jane Smith', 'History', 76),
(3, 'Mike Johnson', 'Computer Science', 95),
(3, 'Mike Johnson', 'Mathematics', 82),
(3, 'Mike Johnson', 'Physics', 79),
(4, 'Sarah Wilson', 'Biology', 91),
(4, 'Sarah Wilson', 'Chemistry', 87),
(4, 'Sarah Wilson', 'Mathematics', 84),
(5, 'David Brown', 'English', 89),
(5, 'David Brown', 'History', 93),
(5, 'David Brown', 'Geography', 81),
(6, 'Lisa Davis', 'Computer Science', 97),
(6, 'Lisa Davis', 'Mathematics', 86),
(6, 'Lisa Davis', 'Physics', 83),
(7, 'Tom Anderson', 'Biology', 88),
(7, 'Tom Anderson', 'Chemistry', 85),
(7, 'Tom Anderson', 'Mathematics', 80),
(8, 'Emma Taylor', 'English', 94),
(8, 'Emma Taylor', 'Literature', 91),
(8, 'Emma Taylor', 'History', 87),
(9, 'Alex Martinez', 'Computer Science', 92),
(9, 'Alex Martinez', 'Mathematics', 89),
(9, 'Alex Martinez', 'Statistics', 85),
(10, 'Sophie Clark', 'Biology', 90),
(10, 'Sophie Clark', 'Chemistry', 88),
(10, 'Sophie Clark', 'Physics', 82);

-- Insert sample attendance data
INSERT INTO attendance (student_id, student_name, attendance_percent) VALUES
(1, 'John Doe', 92.5),
(2, 'Jane Smith', 88.0),
(3, 'Mike Johnson', 95.5),
(4, 'Sarah Wilson', 91.0),
(5, 'David Brown', 87.5),
(6, 'Lisa Davis', 96.0),
(7, 'Tom Anderson', 89.5),
(8, 'Emma Taylor', 93.0),
(9, 'Alex Martinez', 90.5),
(10, 'Sophie Clark', 94.0);

-- Verify the data was inserted
SELECT * FROM marks ORDER BY student_id, subject;
SELECT * FROM attendance ORDER BY student_id;

-- Check marks by subject
SELECT subject, COUNT(*) as total_entries, AVG(marks) as average_marks 
FROM marks 
GROUP BY subject 
ORDER BY subject;

-- Check marks for a specific student (example: student_id = 1)
SELECT * FROM marks WHERE student_id = 1 ORDER BY subject;