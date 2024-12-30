package com.sclab.library.config.apikey;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public enum KnownSecurityException {
    MISSING_API_KEY("Unauthorized", "API key is missing", HttpServletResponse.SC_UNAUTHORIZED),
    INVALID_API_KEY("ERR002", "API key is invalid", HttpServletResponse.SC_UNAUTHORIZED),
    API_KEY_NOT_FOUND("ERR003", "API key not found in the database", HttpServletResponse.SC_UNAUTHORIZED)
    ;


    private final String code;
    private final String message;
    private final int httpStatus;

    KnownSecurityException(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public static Optional<KnownSecurityException> fromMessage(String message) {
        for (KnownSecurityException exception : KnownSecurityException.values()) {
            if (exception.getMessage().equals(message)) {
                return Optional.of(exception);
            }
        }
        return Optional.empty();
    }
}
