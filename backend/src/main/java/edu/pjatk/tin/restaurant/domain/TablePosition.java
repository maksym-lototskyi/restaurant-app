package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record TablePosition(
        @Column(name = "position_x", nullable = false)
        int positionX,
        @Column(name = "position_y", nullable = false)
        int positionY,
        @Column(name = "rotation", nullable = false)
        int rotation
) {
    public TablePosition {
        ValidationUtils.requirePositiveNumber(positionX, "Position X must be a positive number");
        ValidationUtils.requirePositiveNumber(positionY, "Position Y must be a positive number");
        ValidationUtils.requireNonNegativeNumber(rotation, "Rotation must be a non-negative number");
    }
}
