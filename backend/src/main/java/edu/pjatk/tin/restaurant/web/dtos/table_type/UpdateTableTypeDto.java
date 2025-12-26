package edu.pjatk.tin.restaurant.web.dtos.table_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdateTableTypeDto(
        @NotBlank String name,
        @Positive int numberOfSeats
) {
}
