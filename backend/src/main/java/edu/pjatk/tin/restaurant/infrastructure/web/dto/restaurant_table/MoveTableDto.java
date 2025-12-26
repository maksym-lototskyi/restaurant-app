package edu.pjatk.tin.restaurant.infrastructure.web.dto.restaurant_table;

import jakarta.validation.constraints.PositiveOrZero;

public record MoveTableDto(
        @PositiveOrZero
        int positionX,
        @PositiveOrZero
        int positionY,
        @PositiveOrZero
        int rotationDegree
) {
}
