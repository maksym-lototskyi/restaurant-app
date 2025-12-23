package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Hall {
    @Id
    private Long id;

    @Getter
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter
    @Embedded
    private HallDimensions dimensions;

    @OneToMany(mappedBy = "hall", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<RestaurantTable> tables = new HashSet<>();

    public Hall(String name, HallDimensions dimensions, Set<RestaurantTable> tables) {
        this.name = ValidationUtils.requireNonBlank(name, "Hall name cannot be null or blank");
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Hall dimensions cannot be null");
        this.tables = tables;
    }

    public void setName(String name) {
        this.name = ValidationUtils.requireNonBlank(name, "Hall name cannot be null or blank");
    }

    public void setDimensions(HallDimensions dimensions) {
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Hall dimensions cannot be null");
    }

    public void addTable(RestaurantTable table){
        ValidationUtils.requireNonNull(table, "table cannot be null");
        this.tables.add(table);
        table.setHall(this);
    }

    public Set<RestaurantTable> getTables() {
        return Set.copyOf(tables);
    }
}
