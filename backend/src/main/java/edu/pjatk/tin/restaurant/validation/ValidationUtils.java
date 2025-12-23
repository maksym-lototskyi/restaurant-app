package edu.pjatk.tin.restaurant.validation;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new NullObjectException(message);
        }
        return obj;
    }
    public static String requireNonBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new EmptyStringException(message);
        }
        return value;
    }

    public static String requireCorrectStringRegex(String value, String regex, String message) {
        if (value == null || !value.matches(regex)) {
            throw new InvalidStringFormatException(message);
        }
        return value;
    }

    public static <T extends Number> T requirePositiveNumber(T number, String message) {
        if (number == null) {
            throw new NullObjectException(message);
        }
        if (number.doubleValue() <= 0) {
            throw new NonPositiveNumberException(message);
        }
        return number;
    }

    public static <T extends Number> void requireValueInRange(T value, T minValue, T maxValue, String message) {
        if (value == null || minValue == null || maxValue == null) {
            throw new NullObjectException(message);
        }
        if (value.doubleValue() < minValue.doubleValue() || value.doubleValue() > maxValue.doubleValue()) {
            throw new ValueOutOfRangeException(message);
        }
    }

    public static <T extends Number> T requireNonNegativeNumber(T number, String message) {
        if (number == null) {
            throw new NullObjectException(message);
        }
        if (number.doubleValue() < 0) {
            throw new NegativeNumerException(message);
        }
        return number;
    }
}

