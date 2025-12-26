package edu.pjatk.tin.restaurant.use_cases.restaurant_table;

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
