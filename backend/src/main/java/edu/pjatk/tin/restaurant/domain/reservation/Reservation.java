package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class Reservation {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private ReservationId id;
    @Embedded
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "customer_id", nullable = false))
    private RestaurantUserId customerId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "table_id", nullable = false))
    private RestaurantTableId tableId;

    public Reservation(ReservationId id, TimeSlot timeSlot, RestaurantUserId customerId, RestaurantTableId tableId, int numberOfGuests) {
        this.id = ValidationUtil.requireNonNull(id, "Reservation value cannot be null");
        this.timeSlot = ValidationUtil.requireNonNull(timeSlot, "Time slot cannot be null");
        this.customerId = ValidationUtil.requireNonNull(customerId, "Customer value cannot be null");
        this.tableId = ValidationUtil.requireNonNull(tableId, "Table value cannot be null");
        this.numberOfGuests = ValidationUtil.requirePositiveNumber(numberOfGuests, "Number of guests must be a positive number");
    }

    protected Reservation() {
    }

    public static Reservation create(TimeSlot timeSlot, RestaurantUserId customerId, RestaurantTableId tableId, int numberOfGuests) {
        return new Reservation(ReservationId.generate(), timeSlot, customerId, tableId, numberOfGuests);
    }

    public void reschedule(TimeSlot timeSlot) {
        this.timeSlot = ValidationUtil.requireNonNull(timeSlot, "Time slot cannot be null");
    }

    public void cancel() {
        if(this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    public ReservationId getId() {
        return id;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public RestaurantUserId getCustomerId() {
        return customerId;
    }

    public RestaurantTableId getTableId() {
        return tableId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
