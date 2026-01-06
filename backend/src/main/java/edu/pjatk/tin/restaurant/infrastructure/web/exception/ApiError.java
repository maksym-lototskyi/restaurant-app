package edu.pjatk.tin.restaurant.infrastructure.web.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public record ApiError(
        int statusCode,
        String message,
        Map<String, String> fieldErrors,
        String timestamp
) {
    public static ApiError of(int statusCode, String message) {
        return new ApiError(statusCode, message, null, now());
    }

    public static ApiError of(int statusCode, String message, Map<String, String> fieldErrors) {
        return new ApiError(statusCode, message, fieldErrors, now());
    }

    private static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
