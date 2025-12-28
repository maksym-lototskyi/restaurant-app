package edu.pjatk.tin.restaurant.domain.restaurant_user;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String value) {
    private static final String regex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    public Email {
        ValidationUtil.requireNonBlank(value, "Email cannot be blank");
        ValidationUtil.requireCorrectStringRegex(value, regex, "Invalid email format");
    }

    public static Email of(String email) {
        return new Email(email);
    }
}
