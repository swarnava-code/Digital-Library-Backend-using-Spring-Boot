package com.sclab.library.advice;

import com.sclab.library.util.CustomResponseEntity;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException cve) {
        List<String> listOfConstraintViolation = cve.getConstraintViolations()
                .stream()
                .map(v -> String.format("%s | %s", v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.toList());
        logger.error(cve.getConstraintViolations().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "message", "Validation Failed - constraintViolationException",
                        "violations", listOfConstraintViolation
                ));
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleInternalServerError(HttpServerErrorException.InternalServerError ise) {
        logger.error(ise.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "error", "An internal server error occurred: " + ise.getMessage()
                ));
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Object> handleAllError(UnexpectedTypeException ute) {
        logger.error(ute.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "message", "invalid validator",
                        "error", ute.getMessage()
                ));
    }

    // if we use it only it's getting executed only
    // @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllError(Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "error", "exception occur: " + e.getMessage()
                ));
    }

}