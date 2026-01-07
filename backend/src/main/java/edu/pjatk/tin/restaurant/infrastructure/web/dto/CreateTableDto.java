package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.*;

public record CreateTableDto(
        @NotBlank
        @Size(max = 10)
        String tableNumber,
        @PositiveOrZero
        @Max(100)
        int floorNumber,
        @Positive
        @Max(20)
        int numberOfSeats
) {
}
