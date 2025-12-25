package edu.pjatk.tin.restaurant.web.dto.request;

import edu.pjatk.tin.restaurant.domain.value.TablePosition;

public record CreateTableDto(
        String tableNumber,
        String hallName,
        String tableTypeName,
        int positionX,
        int positionY,
        TablePosition.Rotation rotation
) {
}
