package edu.pjatk.tin.restaurant.infrastructure.web.exception;

public record ApiError(
        int statusCode,
        String message,
        String timestamp
) {
    public static ApiError of(int statusCode, String message, String timestamp) {
        return new ApiError(statusCode, message, timestamp);
    }
}
