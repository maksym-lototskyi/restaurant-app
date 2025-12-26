package edu.pjatk.tin.restaurant.domain.table_type;

import edu.pjatk.tin.restaurant.util.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class TableType {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private TableTypeId id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Embedded
    private TableTypeDimensions dimensions;

    public TableType(TableTypeId id, String name, int numberOfSeats, TableTypeDimensions dimensions) {
        this.id = ValidationUtil.requireNonNull(id, "Table type ID cannot be null");
        this.name = ValidationUtil.requireNonBlank(name, "Table type name cannot be null or blank");
        this.dimensions = ValidationUtil.requireNonNull(dimensions, "Table type dimensions cannot be null");
        this.numberOfSeats = ValidationUtil.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
    }

    protected TableType() {
    }

    public static TableType create(String name, int numberOfSeats, TableTypeDimensions dimensions) {
        return new TableType(TableTypeId.generate(), name, numberOfSeats, dimensions);
    }

    public void rename(String name) {
        this.name = ValidationUtil.requireNonBlank(name, "Table type name cannot be null or blank");
    }

    public void resize(TableTypeDimensions dimensions) {
        this.dimensions = ValidationUtil.requireNonNull(dimensions, "Table type dimensions cannot be null");
    }

    public void changeNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = ValidationUtil.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
    }

    public TableTypeId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public TableTypeDimensions getDimensions() {
        return dimensions;
    }
}
