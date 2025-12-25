package edu.pjatk.tin.restaurant.util.validation;

public class NonPositiveNumberException extends RuntimeException {
    public NonPositiveNumberException(String message) {
        super(message);
    }
}
