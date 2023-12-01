package com.sclab.library.service;

import com.sclab.library.entity.Student;
import com.sclab.library.repository.StudentRepository;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity retrieve(String id) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isEmpty()) {
            return CustomResponseEntity.NOT_FOUND();
        }
        return CustomResponseEntity.CUSTOM_MSG(200, optStudent);
    }

    public ResponseEntity update(String id, Student student) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            Student existingStudent = optStudent.get();
            Student updatedStudent = existingStudent.setStudentFieldsOrDefault(student);
            Student replacedStudent = studentRepository.save(updatedStudent);
            return CustomResponseEntity.CUSTOM_MSG(200, replacedStudent);
        }
        return CustomResponseEntity.NOT_FOUND();
    }

    public ResponseEntity delete(String id) {
        var optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            Student student = optStudent.get();
            if (student.getCard() != null) {
                return CustomResponseEntity.CUSTOM_MSG_ERR(HttpStatus.BAD_REQUEST,
                        "message", "You can't delete student who already owning card. First delete card.",
                        "student", student);
            }
            studentRepository.deleteById(id);
            return CustomResponseEntity.CUSTOM_MSG(204, "student deleted");
        }
        return CustomResponseEntity.NOT_FOUND("student not found");
    }
}