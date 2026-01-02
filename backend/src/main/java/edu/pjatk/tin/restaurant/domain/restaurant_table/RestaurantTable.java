package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class RestaurantTable {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RestaurantTableId id;

    @Column(name = "table_number", nullable = false)
    private String tableNumber;
    @Column(name = "floor_number", nullable = false)
    private int floorNumber;
    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    protected RestaurantTable() {
    }

    public RestaurantTable(RestaurantTableId tableId, String tableNumber, int floorNumber, int numberOfSeats) {
        ValidationUtil.requireNonNull(tableId, "TableId cannot be null");
        ValidationUtil.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
        ValidationUtil.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
        this.tableNumber = ValidationUtil.requireNonBlank(tableNumber, "Table number cannot be null or blank");
        this.id = tableId;
    }

    public static RestaurantTable create(String number, int floorNumber, int numberOfSeats) {
        return new RestaurantTable(RestaurantTableId.generate(), number, floorNumber, numberOfSeats);
    }

    public void changeNumber(String number) {
        this.tableNumber = ValidationUtil.requireNonBlank(number, "Table number cannot be null or blank");
    }

    public void changeFloor(int floorNumber) {
        this.floorNumber = ValidationUtil.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }
    public void changeNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = ValidationUtil.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
    }

    public RestaurantTableId getId() {
        return id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
