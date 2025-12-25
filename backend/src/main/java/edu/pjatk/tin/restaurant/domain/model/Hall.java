package edu.pjatk.tin.restaurant.domain.model;

import edu.pjatk.tin.restaurant.domain.value.HallDimensions;
import edu.pjatk.tin.restaurant.domain.value.TablePosition;
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

    @OneToMany(mappedBy = "hall", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<RestaurantTable> tables = new HashSet<>();

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

    public RestaurantTable addTable(String number, TablePosition position, TableType tableType){
        RestaurantTable table = new RestaurantTable(number, position, tableType, this);
        this.tables.add(table);
        return table;
    }

    public Set<RestaurantTable> getTables() {
        return Set.copyOf(tables);
    }

    public void changeFloorNumber(int floorNumber) {
        this.floorNumber = ValidationUtils.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }
}
