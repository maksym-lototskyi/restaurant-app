package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record TablePosition(
        @Column(name = "position_x", nullable = false)
        int positionX,
        @Column(name = "position_y", nullable = false)
        int positionY,
        @Column(name = "rotation", nullable = false)
        @Enumerated(EnumType.STRING)
        Rotation rotation
) {
    public TablePosition {
        ValidationUtils.requirePositiveNumber(positionX, "Position X must be a positive number");
        ValidationUtils.requirePositiveNumber(positionY, "Position Y must be a positive number");
        ValidationUtils.requireNonNull(rotation, "Rotation cannot be null");
    }

    public enum Rotation {
        DEGREE_0(0),
        DEGREE_90(90),
        DEGREE_180(180),
        DEGREE_270(270);

        private final int degree;

        Rotation(int degree) {
            this.degree = degree;
        }

        public int getDegree() {
            return degree;
        }
        public static Rotation fromDegree(int degree) {
            for (Rotation rotation : Rotation.values()) {
                if (rotation.degree == degree) {
                    return rotation;
                }
            }
            throw new IllegalArgumentException("Invalid degree: " + degree);
        }
    }

    public static TablePosition of(int positionX, int positionY, Rotation rotation) {
        return new TablePosition(positionX, positionY, rotation);
    }
}
