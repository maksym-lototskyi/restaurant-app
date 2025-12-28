package edu.pjatk.tin.restaurant.util.validation;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(String message) {
        super(message);
    }
}
