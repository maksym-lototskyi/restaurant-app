package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class TableType {
    @Id
    @Getter
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private TableTypeEnum name;

    @Embedded
    @Getter
    TableTypeDimensions dimensions;

    @OneToMany(mappedBy = "tableType", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private Set<RestaurantTable> tables = new HashSet<>();
    public enum TableTypeEnum{
        TWO_PEOPLE,
        FOUR_PEOPLE,
        SIX_PEOPLE
    }

    public TableType(TableTypeEnum name, TableTypeDimensions dimensions, Set<RestaurantTable> tables) {
        this.name = ValidationUtils.requireNonNull(name, "Table type name cannot be null");
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Table type dimensions cannot be null");
        this.tables = tables;
    }

    public void addTable(RestaurantTable table){
        ValidationUtils.requireNonNull(table, "table cannot be null");
        this.tables.add(table);
        table.setTableType(this);
    }

    public Set<RestaurantTable> getTables() {
        return Set.copyOf(tables);
    }

    public void setName(TableTypeEnum name) {
        this.name = ValidationUtils.requireNonNull(name, "Table type name cannot be null");
    }

    public void setDimensions(TableTypeDimensions dimensions) {
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Table type dimensions cannot be null");
    }
}
