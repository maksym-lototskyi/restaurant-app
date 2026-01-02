package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import jakarta.persistence.EntityExistsException;

@UseCase
public class UpdateTableInfoUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public UpdateTableInfoUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public TableDetails execute(RestaurantTableId tableId, String newTableNumber, int floorNumber, int numberOfSeats){
        var table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table with id " + tableId + " not found"));

        if(restaurantTableRepository.existsByTableNumber(newTableNumber))
            throw new EntityExistsException("Table with number " + newTableNumber + " already exists");

        table.changeNumber(newTableNumber);
        table.changeFloor(floorNumber);
        table.changeNumberOfSeats(numberOfSeats);

        return TableMapper.toDetails(restaurantTableRepository.save(table));
    }
}
