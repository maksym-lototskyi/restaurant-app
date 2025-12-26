package edu.pjatk.tin.restaurant.domain.table_type;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
public class TableType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Embedded
    private TableTypeDimensions dimensions;

    public TableType(String name, int numberOfSeats, TableTypeDimensions dimensions) {
        this.name = ValidationUtils.requireNonBlank(name, "Table type name cannot be null or blank");
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Table type dimensions cannot be null");
        this.numberOfSeats = ValidationUtils.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
    }

    public void rename(String name) {
        this.name = ValidationUtils.requireNonBlank(name, "Table type name cannot be null or blank");
    }

    public void resize(TableTypeDimensions dimensions) {
        this.dimensions = ValidationUtils.requireNonNull(dimensions, "Table type dimensions cannot be null");
    }

    public void changeNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = ValidationUtils.requirePositiveNumber(numberOfSeats, "Number of seats must be a positive number");
    }
}
