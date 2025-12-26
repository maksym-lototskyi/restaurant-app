package edu.pjatk.tin.restaurant.web.dtos.request.restaurant_table;

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
