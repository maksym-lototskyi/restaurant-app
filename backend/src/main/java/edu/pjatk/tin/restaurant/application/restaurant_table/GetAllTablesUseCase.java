package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;

import java.util.List;

@UseCase
public class GetAllTablesUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public GetAllTablesUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public List<TableDetails> execute() {
        return restaurantTableRepository.findAll()
                .stream()
                .map(TableMapper::toDetails)
                .toList();
    }
}
