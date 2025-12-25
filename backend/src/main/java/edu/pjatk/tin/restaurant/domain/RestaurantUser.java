package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant_user")
@NoArgsConstructor
public class RestaurantUser {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private final Set<Reservation> reservations = new HashSet<>();

    public RestaurantUser(String firstName, String lastName, String email, String password) {
        this.firstName = ValidationUtils.requireNonBlank(firstName, "First name cannot be null or blank");
        this.lastName = ValidationUtils.requireNonBlank(lastName, "Last name cannot be null or blank");
        this.email = ValidationUtils.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
        this.password = ValidationUtils.requireNonBlank(password, "Password cannot be null or blank");
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public Set<Reservation> getReservations() {
        return Set.copyOf(reservations);
    }

    public void changeFirstName(String firstName) {
        this.firstName = ValidationUtils.requireNonBlank(firstName, "First name cannot be null or blank");
    }

    public void changeLastName(String lastName) {
        this.lastName = ValidationUtils.requireNonBlank(lastName, "Last name cannot be null or blank");
    }

    public void changeEmail(String email) {
        this.email = ValidationUtils.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
    }

    public void changePassword(String password) {
        this.password = ValidationUtils.requireNonBlank(password, "Password cannot be null or blank");
    }
}
