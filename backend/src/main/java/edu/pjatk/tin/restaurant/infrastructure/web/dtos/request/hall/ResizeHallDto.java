package edu.pjatk.tin.restaurant.infrastructure.web.dtos.request.hall;

import jakarta.validation.constraints.Positive;

public record ResizeHallDto(
        @Positive int length,
        @Positive int width
) {
}
