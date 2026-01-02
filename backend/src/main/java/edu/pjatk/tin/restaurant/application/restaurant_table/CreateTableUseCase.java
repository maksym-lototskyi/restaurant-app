package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import jakarta.persistence.EntityExistsException;

@UseCase
public class CreateTableUseCase {
    private final RestaurantTableRepository restaurantTableRepository;

    public CreateTableUseCase(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public TableDetails execute(String tableNumber, int floorNumber, int numberOfSeats){
        if(restaurantTableRepository.existsByTableNumber(tableNumber))
            throw new EntityExistsException("Table with number " + tableNumber + " already exists");

        RestaurantTable table = RestaurantTable.create(tableNumber, floorNumber, numberOfSeats);

        return TableMapper.toDetails(restaurantTableRepository.save(table));
    }
}
