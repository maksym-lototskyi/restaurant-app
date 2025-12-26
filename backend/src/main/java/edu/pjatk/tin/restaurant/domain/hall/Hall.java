package edu.pjatk.tin.restaurant.domain.hall;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePosition;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Getter
    @Column(name = "floor_number", nullable = false)
    private int floorNumber;

    @Getter
    @Embedded
    private HallDimensions dimensions;

    public Hall(String name, HallDimensions dimensions, int floorNumber) {
        this.name = ValidationUtils.requireNonBlank(name, "Hall name cannot be null or blank");
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Hall dimensions cannot be null");
        this.floorNumber = ValidationUtils.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }

    protected Hall() {
    }

    public void changeName(String name) {
        this.name = ValidationUtils.requireNonBlank(name, "Hall name cannot be null or blank");
    }

    public void resize(HallDimensions dimensions) {
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Hall dimensions cannot be null");
    }

    public void changeFloorNumber(int floorNumber) {
        this.floorNumber = ValidationUtils.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }
}
