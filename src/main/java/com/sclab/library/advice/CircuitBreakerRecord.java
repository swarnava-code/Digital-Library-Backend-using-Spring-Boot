package com.sclab.library.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CircuitBreakerRecord {
    private String message;
    private String exceptionClass;
    private String localisedMessage;
    private String line1StackTrace;

    public CircuitBreakerRecord(Throwable throwable) {
        this.message = throwable.getMessage();
        this.exceptionClass = throwable.getClass().toString();
        this.localisedMessage = throwable.getLocalizedMessage();
        this.line1StackTrace = throwable.getStackTrace()[0].toString();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
