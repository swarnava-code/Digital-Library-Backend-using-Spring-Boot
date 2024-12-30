package com.sclab.library.config.apikey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record UnauthorizedException(String timestamp, int status, String message, String cause, String path, String firstLineOfStackTrace) {

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this).toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}