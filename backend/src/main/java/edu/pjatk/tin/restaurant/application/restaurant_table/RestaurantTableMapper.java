package edu.pjatk.tin.restaurant.application.mapper;

import edu.pjatk.tin.restaurant.domain.model.RestaurantTable;
import edu.pjatk.tin.restaurant.web.dto.response.TableDetailsDto;

public class RestaurantTableMapper {
    public static TableDetailsDto toTableDetailsDto(RestaurantTable table) {
        return new TableDetailsDto(
                table.getId(),
                table.getHall().getName(),
                table.getTableType().getName(),
                table.getPosition().positionX(),
                table.getPosition().positionY(),
                table.getPosition().rotation().getDegree()
        );
    }
}
