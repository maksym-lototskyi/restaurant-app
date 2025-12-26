package edu.pjatk.tin.restaurant.application.restaurant_table;

public record MoveTableDto(
        int positionX,
        int positionY,
        int rotationDegree
) {
}
