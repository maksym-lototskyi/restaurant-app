package edu.pjatk.tin.restaurant.use_cases.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;

import java.util.List;

@UseCase
public class GetTablesInHallUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public GetTablesInHallUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public List<TableDetails> execute(HallId hallId) {
        return restaurantTableRepository.findAllByHallId(hallId)
                .stream()
                .map(TableMapper::toDetails)
                .toList();
    }
}
