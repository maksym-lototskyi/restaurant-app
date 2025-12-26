package edu.pjatk.tin.restaurant.domain.table_type;

import edu.pjatk.tin.restaurant.util.ValidationUtil;
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
        ValidationUtil.requirePositiveNumber(width, "Width must be a positive number");
        ValidationUtil.requirePositiveNumber(length, "Length must be a positive number");
    }

    public static TableTypeDimensions of(int width, int length) {
        return new TableTypeDimensions(width, length);
    }
}
