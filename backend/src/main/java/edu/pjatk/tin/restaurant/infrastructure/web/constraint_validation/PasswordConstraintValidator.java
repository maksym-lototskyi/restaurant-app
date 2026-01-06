package edu.pjatk.tin.restaurant.infrastructure.web.constraint_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator
        implements ConstraintValidator<ValidPassword, String> {

    private static final String DIGIT   = ".*\\d.*";
    private static final String LOWER   = ".*[a-z].*";
    private static final String UPPER   = ".*[A-Z].*";
    private static final String SPECIAL = ".*[!@#$%^&*()/.-].*";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return violation(context, "Password cannot be blank");
        }

        if (value.length() < 8 || value.length() > 64) {
            return violation(context, "Password length must be between 8 and 64 characters");
        }

        if (!value.matches(DIGIT)) {
            return violation(context, "Password must contain at least one digit");
        }

        if (!value.matches(LOWER)) {
            return violation(context, "Password must contain at least one lowercase letter");
        }

        if (!value.matches(UPPER)) {
            return violation(context, "Password must contain at least one uppercase letter");
        }

        if (!value.matches(SPECIAL)) {
            return violation(context, "Password must contain at least one special character");
        }

        return true;
    }

    private boolean violation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}

