package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.domain.value.TablePosition;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
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

    RestaurantTable(String number, TablePosition position, TableType tableType, Hall hall) {
        ValidationUtils.requireNonNull(tableType, "Table type cannot be null");
        ValidationUtils.requireNonNull(hall, "Hall cannot be null");
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
        this.tableType = tableType;
        this.hall = hall;
        validatePosition(position);
    }

    public void changeNumber(String number) {
        this.number = ValidationUtils.requireNonBlank(number, "Table number cannot be null or blank");
    }

    public void move(TablePosition position) {
        this.position = ValidationUtils.requireNonNull(position, "Table position cannot be null");
        validatePosition(position);
    }

    private void validatePosition(TablePosition position) {
        switch (position.rotation()){
            case DEGREE_0, DEGREE_180 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hall.getDimensions().length() - tableType.getDimensions().length(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hall.getDimensions().width() - tableType.getDimensions().width(),
                        "Table position Y must be within hall dimensions");
            }
            case DEGREE_90, DEGREE_270 -> {
                ValidationUtils.requireValueInRange(position.positionX(), 0, hall.getDimensions().length() - tableType.getDimensions().width(),
                        "Table position X must be within hall dimensions");
                ValidationUtils.requireValueInRange(position.positionY(), 0, hall.getDimensions().width() - tableType.getDimensions().length(),
                        "Table position Y must be within hall dimensions");
            }
        }
    }
}
