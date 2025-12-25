package edu.pjatk.tin.restaurant.web.dto.response;

public record TableDetailsDto(
        Long id,
        String hallName,
        String tableTypeName,
        int positionX,
        int positionY,
        int rotation
) {
}
