package edu.pjatk.tin.restaurant.util.validation;

public class EmptyStringException extends RuntimeException {
    public EmptyStringException(String message) {
        super(message);
    }
}
