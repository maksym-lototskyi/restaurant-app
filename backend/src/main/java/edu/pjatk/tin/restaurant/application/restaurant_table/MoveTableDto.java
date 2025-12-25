package edu.pjatk.tin.restaurant.web.dto.request;

public record MoveTableDto(
        int positionX,
        int positionY,
        int rotationDegree
) {
}
