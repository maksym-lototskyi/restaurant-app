package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@UseCase
public class GetAllTablesUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public GetAllTablesUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public Page<TableDetails> execute(int page, int size) {
        return restaurantTableRepository.findAll(PageRequest.of(page, size))
                .map(TableMapper::toDetails);
    }
}
