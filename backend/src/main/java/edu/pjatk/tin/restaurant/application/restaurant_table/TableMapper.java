package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;

public class TableMapper {
    public static TableDetails toDetails(RestaurantTable table) {
        return new TableDetails(
                table.getId().value(),
                table.getTableNumber(),
                table.getFloorNumber(),
                table.getNumberOfSeats()
        );
    }
}
