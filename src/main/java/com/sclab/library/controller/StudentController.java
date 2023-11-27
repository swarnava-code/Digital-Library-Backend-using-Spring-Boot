package com.sclab.library.controller;

import com.sclab.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/student/")
    public String ok(){
        return "";
    }

}