package com.sclab.library.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponseEntity {

    public static ResponseEntity NOT_FOUND(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomMessage().NOT_FOUND());
    }

    public static ResponseEntity NOT_FOUND(String message){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomMessage().NOT_FOUND());
    }

    public static ResponseEntity CREATED(){
        return ResponseEntity
                .status(201)
                .body(new CustomMessage().CREATED());
    }

    public static ResponseEntity CUSTOM_MSG(int code){
        return ResponseEntity
                .status(code)
                .body(new CustomMessage("custom message", code));
    }

    public static ResponseEntity CUSTOM_MSG(int code, String message){
        return ResponseEntity
                .status(code)
                .body(new CustomMessage(message, code));
    }

    public static ResponseEntity CUSTOM_MSG_BODY(int code, Object messageBody){
        return ResponseEntity
                .status(code)
                .body(messageBody);
    }

}