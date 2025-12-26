package edu.pjatk.tin.restaurant.infrastructure.web.dto.hall;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateHallDto(
        @NotBlank String name,
        @Positive int length,
        @Positive int width,
        @PositiveOrZero int floor
) {}

