package edu.pjatk.tin.restaurant.web.dtos.restaurant_table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record CreateTableDto(
        @NotBlank
        String tableNumber,
        @NotNull
        UUID hallId,
        @NotNull
        UUID tableTypeId,
        @PositiveOrZero
        int positionX,
        @PositiveOrZero
        int positionY,
        @PositiveOrZero
        int rotation
) {
}
