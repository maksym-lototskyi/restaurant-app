package edu.pjatk.tin.restaurant.validation;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
