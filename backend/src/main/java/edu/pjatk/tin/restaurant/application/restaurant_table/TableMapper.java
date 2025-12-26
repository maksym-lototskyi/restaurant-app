package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;

public class TableMapper {
    public static TableDetails toDetails(RestaurantTable table) {
        return new TableDetails(
                table.getId().value(),
                table.getNumber(),
                table.getHallId().value(),
                table.getTableTypeId().value(),
                table.getPosition().positionX(),
                table.getPosition().positionY(),
                table.getPosition().rotation().getDegree()
        );
    }
}
