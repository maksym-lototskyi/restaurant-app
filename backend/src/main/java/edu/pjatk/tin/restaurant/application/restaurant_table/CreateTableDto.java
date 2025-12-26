package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePosition;

public record CreateTableDto(
        String tableNumber,
        String hallName,
        String tableTypeName,
        int positionX,
        int positionY,
        TablePosition.Rotation rotation
) {
}
