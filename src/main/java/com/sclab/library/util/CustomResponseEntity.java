package com.sclab.library.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class CustomResponseEntity {
    public final static int BAD_REQUEST = 400;

    public static ResponseEntity NOT_FOUND() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomMessage().NOT_FOUND());
    }

    public static ResponseEntity NOT_FOUND(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomMessage().NOT_FOUND());
    }

    public static ResponseEntity CREATED() {
        return ResponseEntity
                .status(201)
                .body(new CustomMessage().CREATED());
    }

    public static ResponseEntity CUSTOM_MSG(int code) {
        return ResponseEntity
                .status(code)
                .body(new CustomMessage("custom message", code));
    }

    public static ResponseEntity CUSTOM_MSG(int code, String message) {
        return ResponseEntity
                .status(code)
                .body(new CustomMessage(message, code));
    }

    public static ResponseEntity CUSTOM_MSG(int code, Object messageBody) {
        return ResponseEntity
                .status(code)
                .body(messageBody);
    }

    public static ResponseEntity CUSTOM_MSG(int code, Object... keyValuePairs) {
        Map<String, String> map = new HashMap<>();
        int keyValuePairsSize = keyValuePairs.length;
        if (keyValuePairsSize % 2 == 0) {
            for (int i = 1; i < keyValuePairsSize; i+=2) {
                map.put(keyValuePairs[i - 1].toString(), keyValuePairs[i].toString());
            }
        }
        return ResponseEntity
                .status(code)
                .body(new ErrorResponse(map));
    }

}