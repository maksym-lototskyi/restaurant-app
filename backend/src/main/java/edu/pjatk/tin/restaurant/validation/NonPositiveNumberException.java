package edu.pjatk.tin.restaurant.validation;

public class NonPositiveNumberException extends RuntimeException {
    public NonPositiveNumberException(String message) {
        super(message);
    }
}
