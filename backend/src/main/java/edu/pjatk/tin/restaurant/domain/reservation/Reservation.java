package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private RestaurantUser customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;


    public Reservation(TimeSlot timeSlot, RestaurantUser customer, RestaurantTable table) {
        this.timeSlot = ValidationUtils.requireNonNull(timeSlot, "Time slot cannot be null");
        this.customer = ValidationUtils.requireNonNull(customer, "Customer cannot be null");
        this.table = ValidationUtils.requireNonNull(table, "Table cannot be null");
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
}
