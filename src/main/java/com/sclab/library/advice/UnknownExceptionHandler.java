package com.sclab.library.advice;

import com.sclab.library.util.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnknownExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllError(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "error", "Unknown issue found :( ",
                        "exceptionClass", e.getClass(),
                        "message", "This is a unknown issue, Please contact with developer",
                        "details", e.getMessage()
                ));
    }

}