CREATE DATABASE student_portal;
CREATE TABLE admin_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) unique,
    password VARCHAR(255)
);
CREATE TABLE student_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) unique,
    password VARCHAR(255)
);
CREATE TABLE teacher_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) unique,
    password VARCHAR(255)
);
USE student_portal;

select * from admin_table;
USE student_portal;

select * from student_table;
select * from teacher_table;
USE student_portal;

CREATE TABLE marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    marks INT NOT NULL
);

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    attendance_percent DECIMAL(5,2) NOT NULL
);
USE student_portal;
select * from marks;
USE student_portal;
select * from attendance;
USE student_portal;

ALTER TABLE marks ADD subject VARCHAR(255);
USE student_portal;

ALTER TABLE marks MODIFY student_name VARCHAR(255) NULL;
USE student_portal;

DROP TABLE marks;

CREATE TABLE marks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    marks INT NOT NULL  -- Changed from 'score' to 'marks'
);



