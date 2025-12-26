package edu.pjatk.tin.restaurant.infrastructure.web.dto.hall;

import jakarta.validation.constraints.Positive;

public record ResizeHallDto(
        @Positive int length,
        @Positive int width
) {
}
