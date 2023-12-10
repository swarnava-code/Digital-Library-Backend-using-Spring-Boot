package com.sclab.library.controller;

import com.sclab.library.entity.Student;
import com.sclab.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student){
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity retrieveStudent(@PathVariable String id){
        return studentService.retrieve(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable String id,
                                        @RequestBody Student student){
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable String id){
        return studentService.delete(id);
    }

}