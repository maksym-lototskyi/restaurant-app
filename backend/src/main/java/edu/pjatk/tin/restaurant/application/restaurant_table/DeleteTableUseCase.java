package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;

@UseCase
public class DeleteTableUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public DeleteTableUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public void execute(RestaurantTableId tableId) {
        restaurantTableRepository.deleteById(tableId);
    }
}
