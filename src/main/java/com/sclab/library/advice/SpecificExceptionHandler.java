package com.sclab.library.advice;

import com.sclab.library.util.CustomResponseEntity;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SpecificExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(0)
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
    public ResponseEntity<Object> handleUnexpectedTypeException(UnexpectedTypeException ute) {
        logger.error(ute.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "message", "invalid validator",
                        "error", ute.getMessage()
                ));
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<Object> handleConnectException(ConnectException ce) {
        logger.error(ce.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "message", "connection issue - Verify the availability and proper configuration " +
                                "of any external services your application depends on. " +
                                "(e.g., redis, mq, etc)",
                        "error", ce.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidJsonData(HttpMessageNotReadableException
                                                                httpMessageNotReadableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "error", "Data Parsing Failed - You passed invalid value",
                        "exceptionClass", httpMessageNotReadableException.getClass(),
                        "message", httpMessageNotReadableException.getMessage()
                ));
    }

    @ExceptionHandler(KafkaException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleKafkaException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "error", "Kafka connection issue",
                        "exceptionClass", KafkaException.class,
                        "message", "Kafka server might not active"
                ));
    }

}