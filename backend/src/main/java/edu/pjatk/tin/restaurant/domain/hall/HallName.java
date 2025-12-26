package edu.pjatk.tin.restaurant.domain.hall;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;

public record HallName(String value) {
    public HallName {
        ValidationUtils.requireNonBlank(value, "Hall name cannot be null or blank");
        ValidationUtils.requireValueInRange(value.length(), 3, 50, "Hall name must be between 3 and 50 characters long");
    }

    public static HallName of(String name) {
        return new HallName(name);
    }
}
