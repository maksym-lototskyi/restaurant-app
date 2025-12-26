package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
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

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "customer_id", nullable = false))
    private RestaurantUserId customerId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "table_id", nullable = false))
    private RestaurantTableId tableId;

    public Reservation(ReservationId id, TimeSlot timeSlot, RestaurantUserId customerId, RestaurantTableId tableId) {
        this.id = ValidationUtils.requireNonNull(id, "Reservation value cannot be null");
        this.timeSlot = ValidationUtils.requireNonNull(timeSlot, "Time slot cannot be null");
        this.customerId = ValidationUtils.requireNonNull(customerId, "Customer value cannot be null");
        this.tableId = ValidationUtils.requireNonNull(tableId, "Table value cannot be null");
    }

    protected Reservation() {
    }

    public static Reservation create(TimeSlot timeSlot, RestaurantUserId customerId, RestaurantTableId tableId) {
        return new Reservation(ReservationId.generate(), timeSlot, customerId, tableId);
    }

    public void reschedule(TimeSlot timeSlot) {
        this.timeSlot = ValidationUtils.requireNonNull(timeSlot, "Time slot cannot be null");
    }

    public void cancel() {
        if(this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    public void markAsNoShow() {
        if(this.status != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed reservations can be marked as no-show");
        }
        this.status = ReservationStatus.NO_SHOW;
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
}
