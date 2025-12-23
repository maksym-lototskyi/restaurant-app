package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    private Long id;
    @Embedded
    private TimeSlot timeSlot;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User customer;

    @JoinColumn(name = "table_id")
    @ManyToOne
    private RestaurantTable table;

    public Reservation(TimeSlot timeSlot, User customer, RestaurantTable table) {
        this.timeSlot = ValidationUtils.requireNonNull(timeSlot, "Time slot cannot be null");
        this.customer = ValidationUtils.requireNonNull(customer, "Customer cannot be null");
        this.table = ValidationUtils.requireNonNull(table, "Table cannot be null");
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = ValidationUtils.requireNonNull(timeSlot, "Time slot cannot be null");
    }

    void setCustomer(User customer) {
        this.customer = customer;
    }
}
