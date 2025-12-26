package edu.pjatk.tin.restaurant.infrastructure.web.dto.restaurant_table;

import jakarta.validation.constraints.NotNull;

public record UpdateTableInfoDto(
        @NotNull
        String tableNumber
) {
}
