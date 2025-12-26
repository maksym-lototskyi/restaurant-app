package edu.pjatk.tin.restaurant.domain.restaurant_user;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Password {
    private static final String DIGIT = ".*\\d.*";
    private static final String LOWER = ".*[a-z].*";
    private static final String UPPER = ".*[A-Z].*";
    private static final String SPECIAL = ".*[!@#$%^&*()].*";

    private String hashedValue;

    private Password(String hashedValue) {
        this.hashedValue = hashedValue;
    }

    protected Password() {
    }

    public boolean matches(String rawPassword, PasswordHasher hasher) {
        return hasher.matches(rawPassword, this.hashedValue);
    }

    public static Password fromRaw(String password, PasswordHasher hasher) {
        validatePassword(password);
        String hashedPassword = hasher.hash(password);
        return new Password(hashedPassword);
    }

    private static void validatePassword(String password) {
        ValidationUtil.requireNonBlank(password, "Password cannot be null or blank");
        ValidationUtil.requireValueInRange(password.length(), 8, 64, "Password length must be between 8 and 64 characters");
        ValidationUtil.requireCorrectStringRegex(password, DIGIT, "Password must contain at least one digit");
        ValidationUtil.requireCorrectStringRegex(password, LOWER, "Password must contain at least one lowercase letter");
        ValidationUtil.requireCorrectStringRegex(password, UPPER, "Password must contain at least one uppercase letter");
        ValidationUtil.requireCorrectStringRegex(password, SPECIAL, "Password must contain at least one special character");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(hashedValue, password.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }
}
