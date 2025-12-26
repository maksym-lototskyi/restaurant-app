package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.util.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class RestaurantTable {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RestaurantTableId id;

    @Column(name = "table_number", nullable = false)
    private String number;

    @Embedded
    private TablePosition position;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "table_type_id", nullable = false))
    private TableTypeId tableTypeId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "hall_id", nullable = false))
    private HallId hallId;

    protected RestaurantTable() {
    }

    public RestaurantTable(RestaurantTableId tableId, String number, TablePosition position, TableTypeId tableTypeId, HallId hallId) {
        ValidationUtil.requireNonNull(tableTypeId, "Table type value cannot be null");
        ValidationUtil.requireNonNull(hallId, "Hall value cannot be null");
        ValidationUtil.requireNonNull(tableId, "TableId cannot be null");
        this.number = ValidationUtil.requireNonBlank(number, "Table number cannot be null or blank");
        this.position = ValidationUtil.requireNonNull(position, "Table position cannot be null");
        this.tableTypeId = tableTypeId;
        this.hallId = hallId;
        this.id = tableId;
    }

    public static RestaurantTable create(String number, TablePosition position, TableTypeId tableTypeId, HallId hallId) {
        return new RestaurantTable(RestaurantTableId.generate(), number, position, tableTypeId, hallId);
    }

    public void changeNumber(String number) {
        this.number = ValidationUtil.requireNonBlank(number, "Table number cannot be null or blank");
    }

    public void move(TablePosition position) {
        this.position = ValidationUtil.requireNonNull(position, "Table position cannot be null");
    }

    public RestaurantTableId getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public TablePosition getPosition() {
        return position;
    }

    public TableTypeId getTableTypeId() {
        return tableTypeId;
    }

    public HallId getHallId() {
        return hallId;
    }
}
