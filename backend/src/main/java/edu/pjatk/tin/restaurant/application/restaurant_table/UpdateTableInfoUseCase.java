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

    public TableDetails execute(RestaurantTableId tableId, String newTableNumber, int floorNumber){
        var table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table with id " + tableId + " not found"));

        if(restaurantTableRepository.existsByTableNumberExceptSelf(newTableNumber, tableId))
            throw new EntityExistsException("Table with number " + newTableNumber + " already exists");

        table.changeNumber(newTableNumber);
        table.changeFloor(floorNumber);

        return TableMapper.toDetails(restaurantTableRepository.save(table));
    }
}
