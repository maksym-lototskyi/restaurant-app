package edu.pjatk.tin.restaurant.domain.hall;
import edu.pjatk.tin.restaurant.util.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class Hall {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private HallId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false, unique = true))
    private HallName name;

    @Column(name = "floor_number", nullable = false)
    private int floorNumber;

    @Embedded
    private HallDimensions dimensions;

    public Hall(HallId id, HallName name, HallDimensions dimensions, int floorNumber) {
        this.id = ValidationUtil.requireNonNull(id, "Hall ID cannot be null");
        this.name = ValidationUtil.requireNonNull(name, "Hall name cannot be null");
        this.dimensions = ValidationUtil.requireNonNull(dimensions, "Hall dimensions cannot be null");
        this.floorNumber = ValidationUtil.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }

    protected Hall() {
    }

    public static Hall create(HallName name, HallDimensions dimensions, int floorNumber) {
        return new Hall(HallId.generate(), name, dimensions, floorNumber);
    }

    public void changeName(HallName name) {
        this.name = ValidationUtil.requireNonNull(name, "Hall name cannot be null");
    }

    public void resize(HallDimensions dimensions) {
        this.dimensions = ValidationUtil.requireNonNull(dimensions, "Hall dimensions cannot be null");
    }

    public void changeFloorNumber(int floorNumber) {
        this.floorNumber = ValidationUtil.requireNonNegativeNumber(floorNumber, "Floor number cannot be negative");
    }

    public HallId getId() {
        return id;
    }

    public HallName getName() {
        return name;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public HallDimensions getDimensions() {
        return dimensions;
    }
}
