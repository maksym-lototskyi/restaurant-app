package edu.pjatk.tin.restaurant.domain.value;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record TableTypeDimensions(
        @Column(name = "width", nullable = false)
        int width,
        @Column(name = "length", nullable = false)
        int length
) {
    public TableTypeDimensions {
        ValidationUtils.requirePositiveNumber(width, "Width must be a positive number");
        ValidationUtils.requirePositiveNumber(length, "Length must be a positive number");
    }
}
