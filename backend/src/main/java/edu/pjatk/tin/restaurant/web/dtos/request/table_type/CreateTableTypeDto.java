package edu.pjatk.tin.restaurant.web.dtos.request.table_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTableTypeDto(
        @NotBlank String name,
        @Positive int numberOfSeats,
        @PositiveOrZero int length,
        @PositiveOrZero int width) {
}
