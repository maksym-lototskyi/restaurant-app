package edu.pjatk.tin.restaurant.domain.model;

import edu.pjatk.tin.restaurant.domain.value.HallDimensions;
import edu.pjatk.tin.restaurant.domain.value.TablePosition;
import edu.pjatk.tin.restaurant.domain.value.TableTypeDimensions;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false)
    private String number;

    @Embedded
    private TablePosition position;

    @ManyToOne
    @JoinColumn(name = "table_type_id", nullable = false)
    private TableType tableType;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    protected RestaurantTable() {
    }

    RestaurantTable(String number, TablePosition position, TableType tableType, Hall hall) {
        ValidationUtils.requireNonNull(tableType, "Table type cannot be null");
        ValidationUtils.requireNonNull(hall, "Hall cannot be null");
        validatePosition(position, tableType.getDimensions(), hall.getDimensions());
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
        this.tableType = tableType;
        this.hall = hall;
    }

    public void changeNumber(String number) {
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
    }

    public void move(TablePosition position) {
        validatePosition(position, this.tableType.getDimensions(), this.hall.getDimensions());
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
    }

    public static void validatePosition(TablePosition position, TableTypeDimensions dimensions, HallDimensions hallDimensions) {
        switch (position.rotation()){
            case DEGREE_0, DEGREE_180 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hallDimensions.length() - dimensions.length(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hallDimensions.width() - dimensions.width(),
                        "Table position Y must be within hall dimensions");
            }
            case DEGREE_90, DEGREE_270 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hallDimensions.length() - dimensions.width(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hallDimensions.width() - dimensions.length(),
                        "Table position Y must be within hall dimensions");
            }
        }
    }
}
