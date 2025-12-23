package edu.pjatk.tin.restaurant.validation;

public class EmptyStringException extends RuntimeException {
    public EmptyStringException(String message) {
        super(message);
    }
}
