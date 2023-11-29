package com.sclab.library.service;

import com.sclab.library.entity.Student;
import com.sclab.library.repository.StudentRepository;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity create(Student student) {
        student.setId(null);
        Student savedStudent = studentRepository.save(student);
        return CustomResponseEntity.CUSTOM_MSG(201, savedStudent);
    }

    public ResponseEntity update(String id) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isEmpty()) {
            return CustomResponseEntity.NOT_FOUND();
        }
        return CustomResponseEntity.CUSTOM_MSG(200, optStudent);
    }

}