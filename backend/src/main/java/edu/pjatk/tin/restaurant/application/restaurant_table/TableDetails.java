package edu.pjatk.tin.restaurant.application.restaurant_table;

import java.util.UUID;

public record TableDetails(
        UUID id,
        String tableNumber,
        UUID hallId,
        UUID tableTypeId,
        int positionX,
        int positionY,
        int rotation
) {
}
