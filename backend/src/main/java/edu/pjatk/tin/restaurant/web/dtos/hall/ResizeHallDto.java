package edu.pjatk.tin.restaurant.web.dtos.hall;

import jakarta.validation.constraints.Positive;

public record ResizeHallDto(
        @Positive
        int length,
        @Positive
        int width
) {
}
