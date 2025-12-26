package edu.pjatk.tin.restaurant.util;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T requireNonNull(T obj, String message) {
        if(obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }

    public static String requireNonBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireCorrectStringRegex(String value, String regex, String message) {
        if (value == null || !value.matches(regex)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T extends Number> T requirePositiveNumber(T number, String message) {
        if (number == null || number.doubleValue() <= 0) {
            throw new IllegalArgumentException(message);
        }
        return number;
    }

    public static <T extends Number> void requireValueInRange(T value, T minValue, T maxValue, String message) {
        if (value == null || minValue == null || maxValue == null ||
                value.doubleValue() < minValue.doubleValue() || value.doubleValue() > maxValue.doubleValue()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T extends LocalDateTime> T requireStartBeforeEnd(T start, T end, String message) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException(message);
        }
        return start;
    }

}

