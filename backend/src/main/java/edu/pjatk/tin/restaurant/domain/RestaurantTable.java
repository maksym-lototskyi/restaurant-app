package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public final class RestaurantTable {
    @Id
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

    public RestaurantTable(String number, TablePosition position, TableType tableType, Hall hall) {
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
        this.tableType = ValidationUtils.requireNonNull(tableType, "Table type cannot be null");
        this.hall = ValidationUtils.requireNonNull(hall, "Hall cannot be null");
        ValidationUtils.requireValueInRange(position.positionX(), 0, hall.getDimensions().width() - position.positionX(),
                "Table position X must be within hall dimensions");
        ValidationUtils.requireValueInRange(position.positionY(), 0, hall.getDimensions().length() - position.positionY(),
                "Table position Y must be within hall dimensions");
    }

    public void setNumber(String number) {
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
    }

    public void setPosition(TablePosition position) {
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
        ValidationUtils.requireValueInRange(position.positionX(), 0, hall.getDimensions().width() - position.positionX(),
                "Table position X must be within hall dimensions");
        ValidationUtils.requireValueInRange(position.positionY(), 0, hall.getDimensions().length() - position.positionY(),
                "Table position Y must be within hall dimensions");
    }

    void setHall(Hall hall) {
        this.hall = hall;
    }

    void setTableType(TableType tableType) {
        this.tableType = tableType;
    }
}
