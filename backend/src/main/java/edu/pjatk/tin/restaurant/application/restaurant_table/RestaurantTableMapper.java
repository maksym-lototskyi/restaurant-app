package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;

class RestaurantTableMapper {
    static TableDetailsDto toTableDetailsDto(RestaurantTable table) {
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
