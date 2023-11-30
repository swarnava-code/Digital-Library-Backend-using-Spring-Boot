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
                .body(new CustomMessage(message, HttpStatus.NOT_FOUND));
    }

    public static ResponseEntity BAD_REQUEST() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomMessage().BAD_REQUEST());
    }

    public static ResponseEntity BAD_REQUEST(Object... keyValuePairs) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mapToErrorResponse(keyValuePairs));
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

    public static ResponseEntity CUSTOM_MSG(HttpStatus code, Object... keyValuePairs) {
        int codeAsInt = Integer.parseInt(code.toString().split(" ")[0]);
        return CUSTOM_MSG(codeAsInt, keyValuePairs);
    }

    public static ResponseEntity CUSTOM_MSG_ERR(HttpStatus code, Object... keyValuePairs) {
        return CUSTOM_MSG(code, keyValuePairs);
    }

    public static ResponseEntity CUSTOM_MSG(int code, Object... keyValuePairs) {
        Map<String, Object> map = keyValuePairsToMap(keyValuePairs);
        return ResponseEntity
                .status(code)
                .body(new ErrorResponse(map));
    }

    public static ResponseEntity CUSTOM_MSG_OK(int code, Object... keyValuePairs) {
        Map<String, Object> map = keyValuePairsToMap(keyValuePairs);
        return ResponseEntity
                .status(code)
                .body(new OkResponse(map));
    }

    public static Map<String, Object> keyValuePairsToMap(Object... keyValuePairs) {
        Map<String, Object> map = new HashMap<>();
        int keyValuePairsSize = keyValuePairs.length;
        if (keyValuePairsSize % 2 == 0) {
            for (int i = 1; i < keyValuePairsSize; i += 2) {
                map.put(keyValuePairs[i - 1].toString(), keyValuePairs[i]);
            }
        }
        return map;
    }

    public static ErrorResponse mapToErrorResponse(Object... keyValuePairs) {
        return new ErrorResponse(keyValuePairsToMap(keyValuePairs));
    }
}