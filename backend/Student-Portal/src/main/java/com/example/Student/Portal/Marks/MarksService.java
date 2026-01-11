package com.example.Student.Portal.Marks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public Marks saveMarks(Marks marks) {
        return marksRepository.save(marks);
    }

    public List<Marks> getMarksByStudentId(Long studentId) {
        return marksRepository.findByStudentId(studentId);
    }

    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }
}