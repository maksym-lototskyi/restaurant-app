package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTableDto(
        @NotBlank String tableNumber,
        @PositiveOrZero int floorNumber,
        @Positive int numberOfSeats
) {
}
