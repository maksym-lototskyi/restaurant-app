package edu.pjatk.tin.restaurant.use_cases.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class GetTableDetailsUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public GetTableDetailsUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public TableDetails execute(RestaurantTableId tableId){
        return restaurantTableRepository.findById(tableId)
                .map(TableMapper::toDetails)
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + tableId + " not found"));
    }
}
