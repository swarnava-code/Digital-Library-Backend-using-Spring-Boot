package com.sclab.library.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Map<String, Object> error;
}