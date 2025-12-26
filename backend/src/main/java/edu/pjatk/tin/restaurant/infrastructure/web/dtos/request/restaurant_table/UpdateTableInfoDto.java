package edu.pjatk.tin.restaurant.infrastructure.web.dtos.request.restaurant_table;

import jakarta.validation.constraints.NotNull;

public record UpdateTableInfoDto(
        @NotNull
        String tableNumber
) {
}
