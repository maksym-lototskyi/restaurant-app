package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateTableDto(
        @NotBlank
        @Size(max = 10)
        String tableNumber,
        @PositiveOrZero
        int floorNumber
) {
}
